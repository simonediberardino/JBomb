package game.ui.panels.menus;

import game.level.Level;
import game.localization.Localization;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.utils.Paths;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static game.localization.Localization.LOADING;

public class LoadingPanel extends PagePanel {
    public final static int LOADING_DEFAULT_TIMER = 5000;
    private final static int REPAINT_DELAY_MS = 75;
    private final static int TEXT_ANIM_STEP_SIZE = Utility.px(75);
    private final static int FONT_SIZE = Utility.px(75);
    private final static Color FONT_COLOR = new Color(255, 145, 0);
    private final static Color CONTAINER_BACKGROUND = new Color(0, 0, 0, 160);
    private int textCurrX;
    private String text;

    public LoadingPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame, Level level) {
        this(cardLayout, parent, frame, "");
        updateText(level);
        repaint();
    }

    public void updateText(Level level){
        this.text = String.format("%s %s", Localization.get(LOADING), level.toString()).toUpperCase();
    }

    public LoadingPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame, String text) {
        super(cardLayout, parent, frame, Paths.getMainMenuWallpaper());
        this.initialize();
        setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        this.text = text;
    }

    public void initialize() {
        this.text = new String();
        this.textCurrX = (int) Utility.getScreenSize().getWidth();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

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
        int textEndX = rectangleX + (rectangleWidth - textWidth) / 2;

        System.out.println(textCurrX);
        if(textCurrX > textEndX + TEXT_ANIM_STEP_SIZE) {
            textCurrX = textCurrX - TEXT_ANIM_STEP_SIZE;
        }else {
            textCurrX = textEndX;
        }

        boolean centered = textCurrX == textEndX;
        int textY = rectangleY + (rectangleHeight - textHeight) / 2 + fontMetrics.getAscent();
        int textX = textCurrX;

        g.drawString(text, textX, textY);

        if(!centered) {
            TimerTask task = new TimerTask() {
                public void run() {
                    repaint();
                }
            };

            java.util.Timer timer = new Timer();
            timer.schedule(task, REPAINT_DELAY_MS);
        }
    }
}
