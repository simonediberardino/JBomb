package game.ui.panels.menus;

import game.Bomberman;
import game.level.Level;
import game.localization.Localization;
import game.ui.elements.BombermanButton;
import game.ui.elements.Space;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.utils.Paths;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.*;

public class GameOverPanel extends PagePanel {
    private BombermanButton retryButton;
    private BombermanButton mainMenuButton;
    private JPanel listButtonsPanel;

    /**
     * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
     *
     * @param cardLayout the CardLayout to use
     * @param parent the parent JPanel
     * @param frame the BombermanFrame
     */
    public GameOverPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
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
        createMainMenuButton();
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
        retryButton = new BombermanButton(Localization.get(CONTINUE));
        retryButton.addActionListener((v) -> Bomberman.startLevel(Level.getCurrLevel()));
        listButtonsPanel.add(retryButton);
    }

    /**
     * Creates the profileButton and adds it to the listButtonsPanel.
     */
    private void createMainMenuButton() {
        mainMenuButton = new BombermanButton(Localization.get(MAIN_MENU));
        mainMenuButton.addActionListener((v) -> Bomberman.show(MainMenuPanel.class));
        listButtonsPanel.add(mainMenuButton);
    }
}