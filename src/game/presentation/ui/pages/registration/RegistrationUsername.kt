package game.presentation.ui.pages.registration

import game.JBomb
import game.data.data.DataInputOutput
import game.localization.Localization
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.panels.menu.username.UsernameMenuPanelFactory
import game.presentation.ui.panels.models.BoxMenuPanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelOrange
import game.utils.Utility.px
import game.utils.file_system.Paths.mainMenuWallpaper
import java.awt.CardLayout
import javax.swing.JPanel

class RegistrationUsername(cardLayout: CardLayout?, parent: JPanel?, frame: JBombFrame?) : BoxMenuPanel(
        /* cardLayout = */ cardLayout,
        /* parent = */ parent,
        /* frame = */ frame,
        /* title = */ Localization.get(Localization.REGISTRATION_USERNAME),
        /* background = */ mainMenuWallpaper,
        /* isBackEnabled = */ false
) {
    override fun getBoxPanelWidth(): Int = px(1000)

    override fun addCustomElements() {
        boxComponentsPanel.addComponent(
                UsernameMenuPanelFactory().build()
        )

        val next = RedButton(Localization.get(Localization.CONTINUE))
        next.addActionListener { next() }

        boxComponentsPanel.addComponent(next)
        boxComponentsPanel.repaint()
    }

    private fun next() {
        if (DataInputOutput.getInstance().username.isNotBlank()) {
            JBomb.showActivity(RegistrationAvatar::class.java)
        }
    }
}