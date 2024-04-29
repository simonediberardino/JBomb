package game.presentation.ui.pages.servers_list;

import game.Bomberman;
import game.presentation.ui.frames.BombermanFrame;
import game.presentation.ui.pages.AbstractMainMenuPanel;
import game.presentation.ui.pages.play.PlayMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.BACK;
import static game.localization.Localization.get;

public class ServersListMenuPanel extends AbstractMainMenuPanel {
    public ServersListMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 0;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createBackButton());
    }

    private JButton createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> Bomberman.showActivity(PlayMenuPanel.class));
        return b;
    }

    @Override
    public void onShowCallback() {
        super.onShowCallback();
    }
}
