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

        int panelWidth = (int) frame.getPreferredSize().getWidth();
        int panelHeight = (int) frame.getPreferredSize().getHeight();

        if (backgroundImage != null && panelWidth > 0 && panelHeight > 0) {
            if (shouldMaintainAspectRatio()) {
                // Get the image dimensions
                int imgWidth = backgroundImage.getWidth(null);
                int imgHeight = backgroundImage.getHeight(null);

                // Calculate the aspect ratio of the image and the panel
                double imgAspect = (double) imgWidth / imgHeight;
                double panelAspect = (double) panelWidth / panelHeight;

                int newWidth, newHeight;

                // Determine the new dimensions while maintaining the aspect ratio
                if (panelAspect > imgAspect) {
                    // Panel is wider than image, scale by height
                    newHeight = panelHeight;
                    newWidth = (int) (panelHeight * imgAspect);
                } else {
                    // Panel is taller than image, scale by width
                    newWidth = panelWidth;
                    newHeight = (int) (panelWidth / imgAspect);
                }

                // Calculate position to center the image
                int x = (panelWidth - newWidth) / 2;
                int y = (panelHeight - newHeight) / 2;

                // Fill the background with black
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, panelWidth, panelHeight);

                // Draw the scaled image
                g.drawImage(backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), x, y, null);
            } else {
                // Stretch the image to fill the panel
                g.drawImage(backgroundImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH), 0, 0, null);
            }
        }
    }

    // method to be implemented by subclasses to choose whether to maintain the aspect ratio or stretch the image
    public boolean shouldMaintainAspectRatio() {
        return true;
    }

    // Abstract method to be implemented by subclasses for any custom logic when this panel is shown
    public abstract void onShowCallback();
}
