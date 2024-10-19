package game.presentation.ui.pages.multiplayer;

import game.audio.AudioManager;
import game.audio.SoundModel;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PlayerScoreLabel extends JButton {
    private final String playerName;
    private final int score;
    private final int position;
    private final Color backgroundColor = new Color(0, 0, 0, 60);
    private final Color textColor = new Color(255, 255, 255);
    private final Color hoverColor = new Color(100, 100, 100);
    private final int cornerRadius = 15;
    private boolean mouseEntered = false;
    static final int height = Utility.INSTANCE.px(90);

    public PlayerScoreLabel(int width, String playerName, int score, int position) {
        super(playerName);
        this.playerName = playerName;
        this.score = score;
        this.position = position;

        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(textColor);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, Utility.INSTANCE.px(24)));
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered = true;
                repaint();
                AudioManager.getInstance().play(SoundModel.MOUSE_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseEntered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                AudioManager.getInstance().play(SoundModel.CLICK);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - 1;
        int height = getHeight() - 1;

        // Background color
        g2d.setColor(mouseEntered ? hoverColor : backgroundColor);
        g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        // Draw player information (Position, Name, Score)
        g2d.setColor(textColor);

        // Final Position (Emphasized, larger font on the left)
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, Utility.INSTANCE.px(36)));
        String positionText = "#" + position;
        drawAlignedString(g2d, positionText, new Rectangle(10, 0, width / 4, height), SwingConstants.LEFT);

        // Player Name (on the right side)
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Utility.INSTANCE.px(24)));
        drawAlignedString(g2d, playerName, new Rectangle(width / 4, 0, 3 * width / 4 - 20, height / 2), SwingConstants.RIGHT);

        // Score (below the name, on the right side)
        String scoreText = "Score: " + score;
        drawAlignedString(g2d, scoreText, new Rectangle(width / 4, height / 2, 3 * width / 4 - 20, height / 2), SwingConstants.RIGHT);

        g2d.dispose();
    }

    // Helper method to draw aligned text (left or right)
    private void drawAlignedString(Graphics2D g2d, String text, Rectangle rect, int alignment) {
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        int x;

        if (alignment == SwingConstants.LEFT) {
            x = rect.x;  // Align to the left
        } else {
            x = rect.x + (rect.width - metrics.stringWidth(text));  // Align to the right
        }

        g2d.drawString(text, x, y);
    }
}
