package game.presentation.ui.pages.registration

import game.JBomb
import game.localization.Localization
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.panels.menu.avatar.AvatarMenuPanelFactory
import game.presentation.ui.panels.menu.username.UsernameMenuPanelFactory
import game.presentation.ui.panels.models.BoxMenuPanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelOrange
import game.utils.Utility.px
import game.utils.file_system.Paths.mainMenuWallpaper
import java.awt.CardLayout
import javax.swing.JPanel

class RegistrationAvatar(cardLayout: CardLayout?, parent: JPanel?, frame: JBombFrame?) : BoxMenuPanel(
        cardLayout,
        parent,
        frame,
        Localization.get(Localization.REGISTRATION_AVATAR),
        mainMenuWallpaper,
        false
) {
    override fun getBoxPanelWidth(): Int = px(1000)

    override fun addCustomElements() {
        boxComponentsPanel.addComponent(
                AvatarMenuPanelFactory().build()
        )

        val next = RedButton(Localization.get(Localization.CONTINUE))
        next.addActionListener { next() }

        boxComponentsPanel.addComponent(next)
    }

    private fun next() {
        JBomb.showActivity(MainMenuPanel::class.java)
    }
}