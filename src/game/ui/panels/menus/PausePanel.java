package game.ui.panels.menus;

import game.level.Level;
import game.localization.Localization;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.utils.Paths;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.LOADING;
import static game.localization.Localization.PRESS_ESC_CONTINUE;

public class PausePanel extends PagePanel {
    private final static int FONT_SIZE = Utility.px(75);
    private final static Color FONT_COLOR = new Color(255, 145, 0);
    private final static Color CONTAINER_BACKGROUND = new Color(0, 0, 0, 160);
    private final String text;

    public PausePanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Paths.getMainMenuWallpaper());
        setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        this.text = Localization.get(PRESS_ESC_CONTINUE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the background color
        setBackground(Color.WHITE);

        // Calculate the dimensions and position of the rectangle
        int rectangleWidth = getWidth();
        int rectangleHeight = getHeight() / 4;
        int rectangleX = (getWidth() - rectangleWidth) / 2;
        int rectangleY = (int) (((getHeight() - rectangleHeight) / 1.5));

        // Paint the semi-transparent black rectangle
        g.setColor(CONTAINER_BACKGROUND);
        g.fillRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);

        // Set the font and color for the text
        g.setFont(getFont());
        g.setColor(FONT_COLOR);

        // Draw the text in the center of the rectangle
        String text = this.text;
        FontMetrics fontMetrics = g.getFontMetrics(getFont());
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();
        int textX = rectangleX + (rectangleWidth - textWidth) / 2;

        int textY = rectangleY + (rectangleHeight - textHeight) / 2 + fontMetrics.getAscent();

        g.drawString(text, textX, textY);
    }

    @Override
    public void onShowCallback() {

    }
}
