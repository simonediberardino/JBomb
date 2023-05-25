package game.ui.panels.menus;

import game.Bomberman;
import game.level.WorldSelectorLevel;
import game.localization.Localization;
import game.ui.helpers.Padding;
import game.viewelements.bombermanbutton.BombermanButton;
import game.viewelements.misc.Space;
import game.viewelements.bombermanbutton.YellowButton;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.utils.Paths;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.*;
import static game.values.Dimensions.DEFAULT_PADDING;

/**
 * The MenuPanel class represents the main menu screen of the game.
 */
public class MainMenuPanel extends PagePanel {
    private BombermanButton startLevelButton;
    private BombermanButton profileButton;
    private BombermanButton exitButton;
    private JPanel listButtonsPanel;

    /**
     * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
     *
     * @param cardLayout the CardLayout to use
     * @param parent the parent JPanel
     * @param frame the BombermanFrame
     */
    public MainMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Paths.getMainMenuWallpaper());
        setupLayout();
    }

    /**
     * Sets up the layout of the MenuPanel.
     */
    private void setupLayout() {
        setLayout(new GridBagLayout());

        createListButtonsPanel();
        createStartLevelButton();
        createProfileButton();
        createQuitButton();
    }

    /**
     * Creates and adds the listButtonsPanel to the MenuPanel.
     */
    private void createListButtonsPanel() {
        listButtonsPanel = new JPanel();
        listButtonsPanel.setLayout(new GridLayout(0, 1));
        listButtonsPanel.setOpaque(false);
        listButtonsPanel.add(new Space());
        listButtonsPanel.add(new Space());

        add(listButtonsPanel);
    }

    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private void createStartLevelButton() {
        startLevelButton = new YellowButton(Localization.get(PLAY));
        startLevelButton.addActionListener((v) -> Bomberman.startLevel(new WorldSelectorLevel()));
        listButtonsPanel.add(startLevelButton);
    }

    /**
     * Creates the profileButton and adds it to the listButtonsPanel.
     */
    private void createProfileButton() {
        profileButton = new YellowButton(Localization.get(PROFILE));
        profileButton.addActionListener(l -> Bomberman.show(ProfilePanel.class));
        listButtonsPanel.add(profileButton);
    }

    /**
     * Creates the exitButton and adds it to the listButtonsPanel.
     */
    private void createQuitButton() {
        exitButton = new YellowButton(Localization.get(QUIT));
        exitButton.addActionListener(v -> System.exit(0));
        listButtonsPanel.add(exitButton);
    }

    @Override
    public void onShowCallback() {

    }
}