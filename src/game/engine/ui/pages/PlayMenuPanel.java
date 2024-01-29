package game.engine.ui.pages;

import game.Bomberman;
import game.engine.level.levels.lobby.WaitingRoomLevel;
import game.engine.level.levels.lobby.WorldSelectorLevel;
import game.engine.level.online.ClientGameHandler;
import game.engine.level.online.ServerGameHandler;
import game.engine.ui.frames.BombermanFrame;
import game.engine.ui.viewelements.bombermanbutton.RedButton;
import game.engine.ui.viewelements.bombermanbutton.YellowButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

public class PlayMenuPanel extends AbstractMainMenuPanel {
    public PlayMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 3;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createStartLevelButton(), createStartArenaButton(), createServersList(), createBackButton());
    }


    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private JButton createStartLevelButton() {
        JButton startLevelButton = new YellowButton(get(PLAY_CAMPAIGN));
        startLevelButton.addActionListener((v) -> Bomberman.startLevel(new WorldSelectorLevel(), new ServerGameHandler(28960)));
        return startLevelButton;
    }

    private JButton createStartArenaButton() {
        JButton startLevelButton = new YellowButton(get(START_ARENA));
        startLevelButton.addActionListener((v) -> Bomberman.showActivity(ArenaMenuPanel.class));
        return startLevelButton;
    }

    private JButton createServersList() {
        JButton startLevelButton = new YellowButton(get(PLAY_ONLINE));
        // TODO
        startLevelButton.addActionListener((v) -> Bomberman.startLevel(new WaitingRoomLevel(), new ClientGameHandler("localhost", 28960)));
        return startLevelButton;
    }

    private JButton createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> Bomberman.showActivity(MainMenuPanel.class));
        return b;
    }

}
