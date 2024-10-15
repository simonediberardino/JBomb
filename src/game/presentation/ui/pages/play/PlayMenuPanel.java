package game.presentation.ui.pages.play;

import game.JBomb;
import game.domain.level.levels.lobby.WorldSelectorLevel;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.AbstractMainMenuPanel;
import game.presentation.ui.pages.main_menu.MainMenuPanel;
import game.presentation.ui.pages.multiplayer.MultiplayerPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

public class PlayMenuPanel extends AbstractMainMenuPanel {
    public PlayMenuPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 3;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createCampaignButton(), createMultiplayerButton(), createBackButton());
    }

    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private JButton createCampaignButton() {
        JButton startLevelButton = new YellowButton(get(PLAY_CAMPAIGN));
        startLevelButton.addActionListener((v) -> JBomb.startLevel(new WorldSelectorLevel(), null));
        return startLevelButton;
    }

    private JButton createMultiplayerButton() {
        JButton startLevelButton = new YellowButton(get(PLAY_MP));
        startLevelButton.addActionListener(l -> JBomb.showActivity(MultiplayerPanel.class));
        return startLevelButton;
    }

    private JButton createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> JBomb.showActivity(MainMenuPanel.class));
        return b;
    }
}
