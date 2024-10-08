package game.presentation.ui.pages.play;

import game.JBomb;
import game.domain.level.levels.lobby.WorldSelectorLevel;
import game.domain.level.levels.multiplayer.MultiplayerLevelMap1;
import game.domain.level.levels.world1.World1Arena;
import game.domain.level.levels.world1.World1Level5;
import game.domain.level.levels.world2.World2Level3;
import game.domain.match.JBombMatch;
import game.network.gamehandler.ServerGameHandler;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.AbstractMainMenuPanel;
import game.presentation.ui.pages.arena.ArenaMenuPanel;
import game.presentation.ui.pages.main_menu.MainMenuPanel;
import game.presentation.ui.pages.servers_list.ServersListMenuPanel;
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
        return Arrays.asList(createStartLevelButton(), createPlayMp(), createStartArenaButton(), createServersList(), createBackButton());
    }


    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private JButton createStartLevelButton() {
        JButton startLevelButton = new YellowButton(get(PLAY_CAMPAIGN));
        startLevelButton.addActionListener((v) -> JBomb.startLevel(new WorldSelectorLevel(), null));
        return startLevelButton;
    }

    private JButton createPlayMp() {
        JButton startLevelButton = new YellowButton(get(PLAY_MP));
        startLevelButton.addActionListener((v) -> JBomb.startLevel(new MultiplayerLevelMap1(), new ServerGameHandler(JBombMatch.Companion.getDefaultPort())));

        return startLevelButton;
    }

    private JButton createStartArenaButton() {
        JButton startLevelButton = new YellowButton(get(START_ARENA));
        startLevelButton.addActionListener((v) -> JBomb.showActivity(ArenaMenuPanel.class));
        return startLevelButton;
    }

    private JButton createServersList() {
        JButton startLevelButton = new YellowButton(get(PLAY_ONLINE));
        // TODO

        startLevelButton.addActionListener((v) -> JBomb.showActivity(ServersListMenuPanel.class));
        return startLevelButton;
    }

    private JButton createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> JBomb.showActivity(MainMenuPanel.class));
        return b;
    }

}
