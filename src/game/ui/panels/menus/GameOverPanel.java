package game.ui.panels.menus;

import game.Bomberman;
import game.data.DataInputOutput;
import game.level.Level;
import game.localization.Localization;
import game.ui.elements.BombermanButton;
import game.ui.elements.RedButton;
import game.ui.elements.Space;
import game.ui.elements.ToastHandler;
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
        super(cardLayout, parent, frame, Paths.getDeathWallpaper());
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

        add(listButtonsPanel);
    }

    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private void createStartLevelButton() {
        retryButton = new RedButton(Localization.get(PLAY_AGAIN));
        retryButton.addActionListener((v) -> {
            ToastHandler.getInstance().cancel();
            Bomberman.startLevel(Level.getCurrLevel());
        });

        listButtonsPanel.add(retryButton);
    }

    /**
     * Creates the profileButton and adds it to the listButtonsPanel.
     */
    private void createMainMenuButton() {
        mainMenuButton = new RedButton(Localization.get(MAIN_MENU));
        mainMenuButton.addActionListener((v) -> {
            ToastHandler.getInstance().cancel();
            Bomberman.show(MainMenuPanel.class);
        });

        listButtonsPanel.add(mainMenuButton);
    }

    private void showToastMessage() {
        int lives = DataInputOutput.getLives();
        String message = lives > 0
                ? Localization.get(YOU_DIED).replace("%lives%", Integer.toString(lives))
                : Localization.get(RESET_WORLD);

        ToastHandler.getInstance().show(message, true);
    }

    @Override
    public void onShowCallback() {
        showToastMessage();
    }
}