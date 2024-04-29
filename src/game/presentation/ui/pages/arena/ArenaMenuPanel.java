package game.presentation.ui.pages.arena;

import game.Bomberman;
import game.network.gamehandler.ServerGameHandler;
import game.domain.level.levels.world1.World1Arena;
import game.domain.level.levels.world2.World2Arena;
import game.presentation.ui.frames.BombermanFrame;
import game.presentation.ui.pages.AbstractMainMenuPanel;
import game.presentation.ui.pages.play.PlayMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

public class ArenaMenuPanel extends AbstractMainMenuPanel {
    public ArenaMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 2;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createArenaWorld1Button(), createArenaWorld2Button(), createBackButton());
    }

    private JButton createArenaWorld1Button() {
        JButton b = new YellowButton(get(ARENA_WORLD_1));
        b.addActionListener(l -> Bomberman.startLevel(new World1Arena(), new ServerGameHandler(28960)));
        return b;
    }

    private JButton createArenaWorld2Button() {
        JButton b = new YellowButton(get(ARENA_WORLD_2));
        b.addActionListener(l -> Bomberman.startLevel(new World2Arena(), null));
        return b;
    }

    private JButton createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> Bomberman.showActivity(PlayMenuPanel.class));
        return b;
    }

    @Override
    public void onShowCallback() {

    }
}