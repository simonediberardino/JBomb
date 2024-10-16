package game.presentation.ui.pages.main_menu

import game.JBomb
import game.localization.Localization
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.AbstractMainMenuPanel
import game.presentation.ui.pages.play.PlayMenuPanel
import game.presentation.ui.pages.settings.SettingsPanel
import game.presentation.ui.panels.menu.ProfilePanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.properties.RuntimeProperties
import game.usecases.DoUpdateUseCase
import game.utils.ui.ToastUtils;
import java.awt.CardLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JButton
import javax.swing.JPanel

/**
 * The MenuPanel class represents the main menu screen of the game.
 */
class MainMenuPanel
/**
 * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
 *
 * @param cardLayout the CardLayout to use
 * @param parent     the parent JPanel
 * @param frame      the BombermanFrame
 */
(cardLayout: CardLayout?, parent: JPanel?, frame: JBombFrame?) : AbstractMainMenuPanel(cardLayout, parent, frame) {
    private var updateAlertShown = false

    override fun getButtonsPadding(): Int {
        return 3
    }


    override fun getButtons(): MutableList<JButton> {
        return Arrays.asList(createPlayButton(), createProfileButton(), createSettingsButton(), createQuitButton(), createUpdateButton())
    }

    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private fun createPlayButton(): JButton {
        val startLevelButton: JButton = YellowButton(Localization.get(Localization.PLAY))
        startLevelButton.addActionListener(ActionListener { v: ActionEvent? -> JBomb.showActivity(PlayMenuPanel::class.java) })
        return startLevelButton
    }

    /**
     * Creates the profileButton and adds it to the listButtonsPanel.
     */
    private fun createProfileButton(): JButton {
        val profileButton: JButton = YellowButton(Localization.get(Localization.PROFILE))
        profileButton.addActionListener(ActionListener { l: ActionEvent? -> JBomb.showActivity(ProfilePanel::class.java) })
        return profileButton
    }

    private fun createSettingsButton(): JButton {
        val settingsButton: JButton = YellowButton(Localization.get(Localization.SETTINGS))
        settingsButton.addActionListener(ActionListener { l: ActionEvent? -> JBomb.showActivity(SettingsPanel::class.java) })
        return settingsButton
    }

    /**
     * Creates the exitButton and adds it to the listButtonsPanel.
     */
    private fun createQuitButton(): JButton {
        val exitButton: JButton = RedButton(Localization.get(Localization.QUIT))
        exitButton.addActionListener(ActionListener { v: ActionEvent? -> System.exit(0) })
        return exitButton
    }

    private fun createUpdateButton(): JButton? {
        return if (RuntimeProperties.needsUpdate) {
            val settingsButton: JButton = YellowButton(Localization.get(Localization.UPDATE))
            settingsButton.addActionListener(ActionListener { l: ActionEvent? -> DoUpdateUseCase().invokeBlocking() })
            settingsButton
        } else {
            null
        }
    }

    override fun onShowCallback() {
        super.onShowCallback()
        if (!updateAlertShown) {
            updateAlertShown = true
            if (RuntimeProperties.needsUpdate) {
                ToastUtils.show(Localization.get(Localization.NEEDS_UPDATE))
            }
        }
    }
}