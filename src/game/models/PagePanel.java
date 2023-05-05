package game.models;

import game.panels.BombermanFrame;

import javax.swing.*;
import java.awt.*;

public abstract class PagePanel extends JPanel {
    protected final JPanel parent;
    protected final CardLayout cardLayout;
    protected final BombermanFrame frame;

    public PagePanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame){
        this.parent = parent;
        this.cardLayout = cardLayout;
        this.frame = frame;
    }
}
