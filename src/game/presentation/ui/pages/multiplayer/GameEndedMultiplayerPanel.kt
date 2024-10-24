package game.presentation.ui.pages.multiplayer

import game.JBomb
import game.JBomb.showActivity
import game.JBomb.startLevel
import game.domain.level.levels.Level.Companion.currLevel
import game.domain.match.JBombMatch
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.localization.Localization
import game.network.usecases.PingServerUseCase
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.panels.models.CenteredPanel
import game.presentation.ui.panels.models.JBombermanBoxContainerPanel
import game.presentation.ui.viewelements.bombermanbutton.BombermanButton
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelYellow
import game.properties.RuntimeProperties
import game.usecases.ReconnectToServerUseCase
import game.utils.Utility.px
import game.values.Dimensions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.*
import javax.swing.*

class GameEndedMultiplayerPanel(
    private val dialog: JDialog
) : JBombermanBoxContainerPanel(Localization.get(Localization.GAME_ENDED_MP), false, BombermanPanelYellow()) {
    lateinit var exitButton: BombermanButton
    lateinit var playAgainButton: BombermanButton
    var status: Status = Status.IDLE

    init {
        initializeLayout()
    }

    override fun getDefaultBoxPanelWidth(): Int = px(Dimensions.DEFAULT_MAIN_MENU_BOX_SIZE)

    override fun addCustomElements() {
        val panel = createPlayerScorePanel()
        addScrollPane(panel)
        addButtons()
    }

    private fun createPlayerScorePanel(): JPanel {
        val panel = CenteredPanel().apply {
            isOpaque = false
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
        }

        val buttonWidth = defaultBoxPanelWidth - Dimensions.DEFAULT_X_PADDING
        val players = getSortedPlayers()

        players.forEachIndexed { index, bomberEntity ->
            panel.add(PlayerScoreLabel(buttonWidth, bomberEntity.properties.name, bomberEntity.state.score, index + 1))
        }

        return panel
    }

    private fun getSortedPlayers(): List<BomberEntity> {
        return JBomb.match.getEntities()
            .asSequence()
            .filterIsInstance<BomberEntity>()
            .sortedByDescending { it.state.score }
            .toList()
    }

    private fun addScrollPane(panel: JPanel) {
        val scrollPane = JScrollPane(panel).apply {
            preferredSize = Dimension(defaultBoxPanelWidth, px(300))
            verticalScrollBar.unitIncrement = 16
            verticalScrollBar.preferredSize = Dimension(0, 0)
            horizontalScrollBar.preferredSize = Dimension(0, 0)
            isOpaque = false
            viewport.isOpaque = false
            border = null
        }
        addComponent(scrollPane)
    }

    private fun addButtons() {
        playAgainButton = createYellowButton(Localization.get(Localization.PLAY_AGAIN)) {
            JBomb.scope.launch {
                handlePlayAgainAction()
            }
        }

        addComponent(playAgainButton)

        exitButton = createRedButton(Localization.get(Localization.MAIN_MENU)) {
            dialog.dispose()
            showActivity(MainMenuPanel::class.java)
        }
        addComponent(exitButton)

        updateStatus(Status.IDLE)
    }

    private fun createYellowButton(text: String, action: () -> Unit): YellowButton {
        return YellowButton(text).apply {
            addActionListener { action() }
        }
    }

    private fun createRedButton(text: String, action: () -> Unit): RedButton {
        return RedButton(text).apply {
            addActionListener { action() }
        }
    }

    private suspend fun handlePlayAgainAction() {
        if (status != Status.IDLE)
            return

        updateStatus(Status.CONNECTING)

        if (JBomb.match.wasServer) {
            dialog.dispose()
            currLevel?.let { startLevel(it, JBomb.match.onlineGameHandler) }
        } else {
            val lastServer = RuntimeProperties.lastConnectedIp
            val tokens = lastServer.split(":").dropLastWhile { it.isEmpty() }
            val ipv4 = tokens[0]
            val port: Int = tokens.getOrNull(1)?.toInt() ?: JBombMatch.port // Default port if parsing fails

            if (PingServerUseCase(ipv4, port).invoke()) {
                dialog.dispose()
                ReconnectToServerUseCase().invokeBlocking()
            } else {
                updateStatus(Status.WAIT_FOR_HOST)
                delay(3000L)
                updateStatus(Status.IDLE)
            }
        }
    }

    private fun updateStatus(status: Status) {
        this.status = status

        when(status) {
            Status.CONNECTING -> playAgainButton.text = Localization.get(Localization.CONNECTING)
            Status.IDLE -> playAgainButton.text = Localization.get(Localization.PLAY_AGAIN)
            Status.WAIT_FOR_HOST -> playAgainButton.text = Localization.get(Localization.WAIT_FOR_HOST)
        }
    }

    enum class Status {
        CONNECTING,
        IDLE,
        WAIT_FOR_HOST
    }

    companion object {
        fun showSummary() {
            val dialog = createDialog(JBomb.JBombFrame)
            val panel = GameEndedMultiplayerPanel(dialog)
            val blurBackground = createBlurBackground(panel)

            dialog.contentPane.add(blurBackground)
            dialog.isVisible = true
        }

        private fun createDialog(parentFrame: JFrame): JDialog {
            return JDialog(parentFrame).apply {
                isUndecorated = true
                size = parentFrame.size
                setLocationRelativeTo(null)
                background = Color(0, 0, 0, 0)
                modalityType = Dialog.ModalityType.MODELESS
                isResizable = false
                defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
            }
        }

        private fun createBlurBackground(panel: GameEndedMultiplayerPanel): JPanel {
            return object : JPanel() {
                override fun paintComponent(g: Graphics) {
                    super.paintComponent(g)
                    g.color = Color(0, 0, 0, 150)
                    g.fillRect(0, 0, width, height)
                }
            }.apply {
                isOpaque = false
                layout = BorderLayout()
                add(panel, BorderLayout.CENTER)
            }
        }
    }
}
