package game.presentation.ui.pages.game_over;


import game.JBomb;
import game.data.data.DataInputOutput;
import game.domain.level.levels.ArenaLevel;
import game.domain.level.levels.Level;
import game.domain.level.levels.lobby.WorldSelectorLevel;
import game.localization.Localization;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.BaseMenu;
import game.presentation.ui.pages.main_menu.MainMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.utils.file_system.Paths;
import game.utils.ui.ToastUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

public class ArenaGameOverPanel extends BaseMenu {
    @Override
    public boolean blurBackground() {
        return false;
    }

    /**
     * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
     *
     * @param cardLayout the CardLayout to use
     * @param parent     the parent JPanel
     * @param frame      the BombermanFrame
     */
    public ArenaGameOverPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame, Paths.getDeathWallpaper());
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createTitle(), createMainMenuButton());
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
    public boolean showAppLogo() {
        return false;
    }

    private JButton createTitle() {
        return new YellowButton(Localization.get(Localization.ARENA_DIED));
    }

    private JButton createMainMenuButton() {
        JButton mainMenuButton = new RedButton(Localization.get(MAIN_MENU));
        mainMenuButton.addActionListener((v) -> {
            ToastUtils.INSTANCE.cancel();
            JBomb.showActivity(MainMenuPanel.class);
        });

        return mainMenuButton;
    }

    private void showToastMessage() {
    }

    @Override
    public void onShowCallback() {
        showToastMessage();
    }
}