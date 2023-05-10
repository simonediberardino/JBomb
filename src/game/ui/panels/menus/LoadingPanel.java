package game.ui.panels.menus;

import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;

import javax.swing.*;
import java.awt.*;

public class LoadingPanel extends PagePanel {
    public LoadingPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame, String imagePath) {
        super(cardLayout, parent, frame, imagePath);
    }
}
