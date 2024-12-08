package game.presentation.ui.pages.multiplayer

import game.JBomb.showActivity
import game.localization.Localization
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.AbstractMainMenuPanel
import game.presentation.ui.pages.arena.ArenaMenuPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.pages.server_browser.ServersListMenuPanel
import game.presentation.ui.pages.multiplayer.MultiplayerSelectMapPanel
import game.presentation.ui.panels.game.PagePanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import java.awt.CardLayout
import javax.swing.JButton
import javax.swing.JPanel

class MultiplayerPanel(
    cardLayout: CardLayout,
    parent: JPanel,
    frame: JBombFrame
) : AbstractMainMenuPanel(cardLayout, parent, frame) {

    override fun getButtonsPadding(): Int = 2

    override fun getButtons(): MutableList<JButton> = mutableListOf(
        createButton(Localization.get(Localization.PLAY_ONLINE), ::YellowButton, ServersListMenuPanel::class.java),
        createButton(Localization.get(Localization.PLAY_PVP), ::YellowButton, MultiplayerSelectMapPanel::class.java),
        createButton(Localization.get(Localization.START_ARENA), ::YellowButton, ArenaMenuPanel::class.java),
        createButton(Localization.get(Localization.BACK), ::RedButton, MainMenuPanel::class.java)
    )

    override fun blurBackground(): Boolean {
        return true
    }

    private fun createButton(
        text: String,
        buttonConstructor: (String) -> JButton,
        targetActivity: Class<out PagePanel>
    ): JButton = buttonConstructor(text).apply {
        addActionListener { showActivity(targetActivity) }
    }
}
