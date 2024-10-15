package game.presentation.ui.pages.multiplayer

import game.JBomb.showActivity
import game.localization.Localization
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.AbstractMainMenuPanel
import game.presentation.ui.pages.arena.ArenaMenuPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.pages.servers_list.ServersListMenuPanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import java.awt.CardLayout
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JPanel

class MultiplayerPanel(
        cardLayout: CardLayout,
        parent: JPanel, frame:
        JBombFrame)
    : AbstractMainMenuPanel(cardLayout, parent, frame)
{
    override fun getButtonsPadding(): Int = 3

    override fun getButtons(): MutableList<JButton> =
            mutableListOf(createPlayMp(), createStartArenaButton(), createServersList(), createBackButton())

    private fun createPlayMp(): JButton {
        val startLevelButton: JButton = YellowButton(Localization.get(Localization.PLAY_PVP))
        startLevelButton.addActionListener { showActivity(ServersListMenuPanel::class.java) }
        return startLevelButton
    }

    private fun createStartArenaButton(): JButton {
        val startLevelButton: JButton = YellowButton(Localization.get(Localization.START_ARENA))
        startLevelButton.addActionListener { showActivity(ArenaMenuPanel::class.java) }
        return startLevelButton
    }

    private fun createServersList(): JButton {
        val startLevelButton: JButton = YellowButton(Localization.get(Localization.PLAY_ONLINE))
        startLevelButton.addActionListener { showActivity(ServersListMenuPanel::class.java) }
        return startLevelButton
    }

    private fun createBackButton(): JButton {
        val b: JButton = RedButton(Localization.get(Localization.BACK))
        b.addActionListener { showActivity(MainMenuPanel::class.java) }
        return b
    }
}