package game

import game.audio.AudioManager
import game.data.data.DataInputOutput
import game.domain.level.levels.Level
import game.domain.match.BomberManMatch
import game.domain.tasks.GarbageCollectorTask
import game.localization.Localization
import game.network.gamehandler.OnlineGameHandler
import game.presentation.ui.frames.BombermanFrame
import game.presentation.ui.pages.error.NetworkErrorPage
import game.presentation.ui.pages.loading.LoadingPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.panels.game.CustomSoundMode
import game.presentation.ui.panels.game.MatchPanel
import game.presentation.ui.panels.game.PagePanel
import game.presentation.ui.viewelements.misc.ToastHandler
import kotlinx.coroutines.launch
import java.awt.Component
import java.util.*
import javax.swing.SwingUtilities

object Bomberman {
    lateinit var match: BomberManMatch

    @JvmStatic
    lateinit var bombermanFrame: BombermanFrame
    private var currentPage: Class<out PagePanel>? = null

    /**
     * Starts the Java Application;
     */
    @JvmStatic
    fun main(args: Array<String>) {
        retrievePlayerData()
        startGarbageCollectorTask()
        start()
    }

    private fun start() {
        bombermanFrame = BombermanFrame()
        bombermanFrame.create()
        showActivity(MainMenuPanel::class.java)
        ToastHandler.getInstance().show(Localization.get(Localization.WELCOME_TEXT).replace("%user%", DataInputOutput.getInstance().username))
    }

    private fun startGarbageCollectorTask() {
        GarbageCollectorTask().start()
    }

    private fun retrievePlayerData() {
        DataInputOutput.getInstance().retrieveData()
    }

    @JvmStatic
    fun quitMatch() {
        destroyLevel(true)
        showActivity(MainMenuPanel::class.java)
    }

    fun destroyLevel(disconnect: Boolean) {
        bombermanFrame.removeKeyListener(match.controllerManager)
        match.destroy(disconnect)
    }

    /**
     * Starts a new level and destroys the previous one;
     *
     */
    private fun doStartLevel(level: Level, disconnect: Boolean, onlineGameHandler: OnlineGameHandler?) {
        if (this::match.isInitialized) {
            destroyLevel(disconnect)
        }

        match = BomberManMatch(level, onlineGameHandler)

        match.scope.launch {
            bombermanFrame.initGamePanel()
            match.currentLevel.start(bombermanFrame.pitchPanel)
            bombermanFrame.addKeyListener(match.controllerManager)
            bombermanFrame.pitchPanel.addMouseListener(match.mouseControllerManager)
            bombermanFrame.pitchPanel.addMouseMotionListener(match.mouseControllerManager)
            showActivity(MatchPanel::class.java)

            match.connect()
        }
    }

    @JvmStatic
    fun startLevel(level: Level, onlineGameHandler: OnlineGameHandler?) {
        startLevel(level, onlineGameHandler, true) {}
    }

    @JvmStatic
    fun startLevel(
            level: Level,
            onlineGameHandler: OnlineGameHandler?,
            disconnect: Boolean = true,
            callback: () -> Unit,
    ) {
        bombermanFrame.loadingPanel.initialize()
        bombermanFrame.loadingPanel.updateText(level)
        bombermanFrame.loadingPanel.setCallback {
            doStartLevel(level, disconnect, onlineGameHandler)
            callback()
        }

        showActivity(LoadingPanel::class.java)
    }

    fun networkError(error: String?) {
        if (!isInGame) return

        destroyLevel(true)

        val formattedError = error?.let {
            Localization.get(Localization.ERROR).replace("%error%", error)
        } ?: Localization.get(Localization.GAME_ENDED)

        NetworkErrorPage.setError(formattedError)

        SwingUtilities.invokeLater {
            showActivity(NetworkErrorPage::class.java)
        }
    }

    /**
     * Shows a new page and starts its background sound;
     *
     * @param page
     */
    @JvmStatic
    fun showActivity(page: Class<out PagePanel?>) {
        if (page == currentPage)
            return

        bombermanFrame.cardLayout.show(bombermanFrame.parentPanel, page.simpleName)
        currentPage = page

        // Gets the component with the passed class and fires its onShowCallback;
        val shownComponentOpt = Arrays.stream(
                bombermanFrame.parentPanel.components
        ).filter { c: Component? ->
            c!!.javaClass == page
        }.findFirst()

        val shownComponent = shownComponentOpt.orElse(null)

        if (shownComponent is PagePanel)
            shownComponent.onShowCallback()

        if (shownComponent !is CustomSoundMode) {
            AudioManager.getInstance().playBackgroundSong()
        }
    }

    val isGameEnded: Boolean
        get() = !match.gameState || !Bomberman.isInGame

    val isInGame: Boolean
        get() {
            val match = match
            val currentLevel = match.currentLevel
            return match.gameState && currentLevel.info.worldId >= 0
        }
}
