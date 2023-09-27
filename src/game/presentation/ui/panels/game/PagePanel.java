package game.presentation.ui.panels.game;

import game.presentation.ui.frames.JBombFrame;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

public abstract class PagePanel extends JPanel {
    protected final JPanel parent;
    protected final CardLayout cardLayout;
    protected final JBombFrame frame;
    private final String imagePath;

    public PagePanel(CardLayout cardLayout, JPanel parent, JBombFrame frame, String imagePath) {
        this.parent = parent;
        this.cardLayout = cardLayout;
        this.frame = frame;
        this.imagePath = imagePath;
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image backgroundImage = Utility.INSTANCE.loadImage(imagePath);

        int width = (int) frame.getPreferredSize().getWidth();
        int height = (int) frame.getPreferredSize().getHeight();

        // Scale the background image to fit the size of the panel and draw it
        if (width != 0 && height != 0) {
            try{
                g.drawImage(backgroundImage.getScaledInstance(width, height, 1), 0, 0, null);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void onShowCallback();
}
