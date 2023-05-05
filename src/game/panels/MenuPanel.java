package game.panels;

import game.BomberMan;
import game.models.PagePanel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends PagePanel {
    // TEST
    public MenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);

        JButton jButton = new JButton("START");

        jButton.addActionListener((v) -> {
            BomberMan.getInstance().getGameFrame().initGamePanel();
            frame.show(MatchPanel.class);
        });

        add(jButton);
    }
}
