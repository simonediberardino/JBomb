package game.ui.pages;

import game.Bomberman;
import game.ui.frames.BombermanFrame;
import game.ui.panels.menu.ProfilePanel;
import game.ui.viewelements.bombermanbutton.RedButton;
import game.ui.viewelements.bombermanbutton.YellowButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

/**
 * The MenuPanel class represents the main menu screen of the game.
 */
public class MainMenuPanel extends AbstractMainMenuPanel {
    /**
     * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
     *
     * @param cardLayout the CardLayout to use
     * @param parent     the parent JPanel
     * @param frame      the BombermanFrame
     */
    public MainMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 3;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createPlayButton(), createProfileButton(), createSettingsButton(), createQuitButton());
    }

    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private JButton createPlayButton() {
        JButton startLevelButton = new YellowButton(get(PLAY));
        startLevelButton.addActionListener((v) -> Bomberman.showActivity(PlayMenuPanel.class));
        return startLevelButton;
    }

    /**
     * Creates the profileButton and adds it to the listButtonsPanel.
     */
    private JButton createProfileButton() {
        JButton profileButton = new YellowButton(get(PROFILE));
        profileButton.addActionListener(l -> Bomberman.showActivity(ProfilePanel.class));
        return profileButton;
    }

    private JButton createSettingsButton() {
        JButton settingsButton = new YellowButton(get(SETTINGS));
        settingsButton.addActionListener(l -> Bomberman.showActivity(SettingsPanel.class));
        return settingsButton;
    }

    /**
     * Creates the exitButton and adds it to the listButtonsPanel.
     */
    private JButton createQuitButton() {
        JButton exitButton = new RedButton(get(QUIT));
        exitButton.addActionListener(v -> System.exit(0));
        return exitButton;
    }

    @Override
    public void onShowCallback() {

    }
}