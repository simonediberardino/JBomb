package game.presentation.ui.pages.multiplayer;

import game.audio.AudioManager;
import game.audio.SoundModel;
import game.domain.world.domain.entity.geo.Direction;
import game.localization.Localization;
import game.utils.Utility;
import game.utils.file_system.Paths;
import game.utils.skin.SkinUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PlayerScoreLabel extends JButton {
    static final int height = Utility.INSTANCE.px(90);
    private static final Dimension playerImageDimension = new Dimension(Utility.INSTANCE.px(75), Utility.INSTANCE.px(75));
    private final String playerName;
    private final int kills;
    private final int position;
    private final Color backgroundColor = new Color(0, 0, 0, 60);
    private final Color textColor = new Color(255, 255, 255);
    private final Color hoverColor = new Color(100, 100, 100);
    private final int cornerRadius = 15;
    private final int skinId;
    private boolean mouseEntered = false;

    public PlayerScoreLabel(int width, String playerName, int skinId, int kills, int position) {
        super(playerName);
        this.playerName = playerName;
        this.kills = kills;
        this.position = position;
        this.skinId = skinId;

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

        // Player Name (on the right side, taking available space without overlapping image)
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Utility.INSTANCE.px(24)));

        // Define space for the name and score, excluding space for the image on the right
        int imageWidth = playerImageDimension.width + 10;  // Image width plus padding
        int availableWidthForText = width - imageWidth;    // Total available space minus image space

        // Define the rectangle for the player name, adjusting its width based on image space
        Rectangle nameRect = new Rectangle(width / 4, 0, (int) (availableWidthForText / 1.5 - 20), height / 2);
        drawAlignedString(g2d, playerName, nameRect, SwingConstants.RIGHT);

        // Score (below the name, adjusting similarly for available space)
        String scoreText = Localization.get(Localization.KILLS) + ": " + kills;
        Rectangle scoreRect = new Rectangle(width / 4, height / 2, (int) (availableWidthForText / 1.5 - 20), height / 2);
        drawAlignedString(g2d, scoreText, scoreRect, SwingConstants.RIGHT);

        // Load and draw the user image/logo on the right
        Image userImage = getPlayerImage();
        int imageX = width - playerImageDimension.width - 10;  // X position: 10px padding from the right
        int imageY = (height - playerImageDimension.height) / 2;  // Y position: vertically centered

        // Draw the image
        g2d.drawImage(userImage, imageX, imageY, playerImageDimension.width, playerImageDimension.height, this);

        g2d.dispose();
    }

    private Image getPlayerImage() {
        String skinName = SkinUtilities.INSTANCE.getSkinName(String.valueOf(skinId));
        String playerPath = String.format("%s/player/%s", Paths.getEntitiesFolder(), skinName);

        String imagePath = String.format("%s/player_%s_%d.png", playerPath, Direction.DOWN.toString().toLowerCase(), 0);

        Image image = Utility.INSTANCE.loadImage(imagePath);

        return image.getScaledInstance((int) playerImageDimension.getWidth(), (int) playerImageDimension.getHeight(), 0);
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
