package game.presentation.ui.pages.game_over;

import game.JBomb;
import game.data.data.DataInputOutput;
import game.domain.level.levels.Level;
import game.domain.level.levels.lobby.WorldSelectorLevel;
import game.localization.Localization;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.BaseMenu;
import game.presentation.ui.pages.main_menu.MainMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.utils.ui.ToastUtils;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

public class GameOverPanel extends BaseMenu {
    private JButton retryButton;

    /**
     * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
     *
     * @param cardLayout the CardLayout to use
     * @param parent     the parent JPanel
     * @param frame      the BombermanFrame
     */
    public GameOverPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame, Paths.getDeathWallpaper());
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createStartLevelButton(), createMainMenuButton());
    }

    @Override
    protected JPanel getRightPanel() {
        return null;
    }

    @Override
    protected JPanel getLeftPanel() {
        return null;
    }

    @Override
    protected int getButtonsPadding() {
        return 1;
    }

    private JButton createStartLevelButton() {
        retryButton = new RedButton("");
        retryButton.addActionListener((v) -> {
            boolean hasLives = DataInputOutput.getInstance().getLives() > 0;

            ToastUtils.INSTANCE.cancel();

            Level level = hasLives ? Level.Companion.getCurrLevel() : new WorldSelectorLevel();
            JBomb.startLevel(level, JBomb.match.getOnlineGameHandler());
        });

        updatePlayAgainButtonText();
        return retryButton;
    }

    private JButton createMainMenuButton() {
        JButton mainMenuButton = new RedButton(Localization.get(MAIN_MENU));
        mainMenuButton.addActionListener((v) -> {
            ToastUtils.INSTANCE.cancel();
            JBomb.showActivity(MainMenuPanel.class);
        });

        return mainMenuButton;
    }

    private void updatePlayAgainButtonText() {
        boolean hasLives = DataInputOutput.getInstance().getLives() > 0;
        String text = hasLives ? Localization.get(PLAY_AGAIN) : Localization.get(RESET_WORLD);
        retryButton.setText(text);
    }

    private void showToastMessage() {
        Level lastLevel = Level.Companion.getCurrLevel();
        String diedMessage = lastLevel.getInfo().getDiedMessage();
        ToastUtils.INSTANCE.show(diedMessage, true, true);
    }

    @Override
    public void onShowCallback() {
        showToastMessage();
        updatePlayAgainButtonText();
    }
}