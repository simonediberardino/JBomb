package game.presentation.ui.pages.registration

import game.JBomb
import game.data.data.DataInputOutput
import game.localization.Localization
import game.presentation.ui.elements.createBlurBackground
import game.presentation.ui.elements.createDialog
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.panels.game.PagePanel
import game.presentation.ui.panels.menu.avatar.AvatarMenuPanelFactory
import game.presentation.ui.panels.menu.username.UsernameMenuPanelFactory
import game.presentation.ui.panels.models.BoxMenuPanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.utils.Utility.px
import game.utils.file_system.Paths.mainMenuWallpaper
import java.awt.CardLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JPanel

class Registration(cardLayout: CardLayout?, parent: JPanel?, frame: JBombFrame?) : PagePanel(
        cardLayout,
        parent,
        frame,
        mainMenuWallpaper,
) {
    override fun blurBackground(): Boolean {
        return true
    }

    override fun onShowCallback() {
        showRegistrationDialog()
    }

    private fun showRegistrationDialog() {
        val dialog = createDialog(JBomb.JBombFrame)

        val welcomeButton = YellowButton(Localization.get(Localization.WELCOME_TEXT_ANONYMOUS))

        val usernamePanel = UsernameMenuPanelFactory().build()
        val avatarPanel = AvatarMenuPanelFactory().build()
        val continueButton = RedButton(Localization.get(Localization.CONTINUE)).also {
            it.addActionListener {
                val savedUsername = DataInputOutput.getInstance().username
                if (savedUsername.isNotEmpty()) {
                    dialog.dispose()
                    JBomb.showActivity(MainMenuPanel::class.java)
                }
            }
        }

        // Set up GridBagLayout with proper constraints
        val panel = JPanel(GridBagLayout()).apply {
            alignmentY = TOP_ALIGNMENT
            alignmentX = CENTER_ALIGNMENT
            isOpaque = false // Make the panel transparent
            preferredSize = JBomb.JBombFrame.preferredSize
        }

        // Create a GridBagConstraints object for proper alignment
        val gbc = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL  // Ensure the components expand horizontally
            weightx = 1.0  // Allow components to stretch horizontally
            insets = Insets(5, 0, 5, 0)  // Set vertical spacing between components
        }

        gbc.gridx = 0  // First column
        gbc.gridy = 0  // First row
        gbc.fill = GridBagConstraints.NONE  // Don't stretch the button horizontally
        gbc.anchor = GridBagConstraints.CENTER  // Center the button
        panel.add(welcomeButton, gbc)

        // Add usernamePanel to the panel below the "Welcome" button
        gbc.gridy = 1  // Second row
        panel.add(usernamePanel, gbc)

        // Add avatarPanel below usernamePanel
        gbc.gridy = 2  // Third row
        panel.add(avatarPanel, gbc)

        gbc.gridy = 3  // Fourth row
        gbc.fill = GridBagConstraints.NONE  // Don't stretch the button horizontally
        gbc.anchor = GridBagConstraints.CENTER  // Center the button
        panel.add(continueButton, gbc)

        val blurBackground = createBlurBackground(panel)

        dialog.contentPane.add(blurBackground)
        dialog.pack() // Adjusts the dialog size to fit the components' preferred sizes

        dialog.isVisible = true
    }
}