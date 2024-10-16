package game.presentation.ui.pages.server_browser;

import game.audio.AudioManager;
import game.audio.SoundModel;
import game.domain.events.models.RunnablePar;
import game.utils.Utility;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Custom JButton class for displaying server information
class ServerButton extends JButton {
    private final ServerInfo server;
    private final Color backgroundColor = new Color(0, 0, 0, 60);
    private final Color textColor = new Color(255, 255, 255);
    private final Color hoverColor = new Color(100, 100, 100);
    private final int cornerRadius = 15;
    private boolean mouseEntered = false;
    static final int height = Utility.INSTANCE.px(90);

    public ServerButton(int width, ServerInfo server, RunnablePar listener) {
        super(server.getName());
        this.server = server;

        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(textColor);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, Utility.INSTANCE.px(24)));
        setPreferredSize(new Dimension(width, height));
        addActionListener(e -> listener.execute(server.getFullIp()));

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

        // Draw server information (Name, Players, Ping)
        g2d.setColor(textColor);
        g2d.setFont(getFont());

        // Server Name
        String serverName = server.getName();
        drawCenteredString(g2d, serverName, new Rectangle(10, 0, width - 20, height / 3), g2d.getFont());

        // Player Count
        String players = "Players: " + server.getPlayers();
        drawCenteredString(g2d, players, new Rectangle(10, height / 3, width - 20, height / 3), g2d.getFont());

        // Ping
        String ping = "Ping: " + server.getPing() + "ms";
        drawCenteredString(g2d, ping, new Rectangle(10, 2 * height / 3, width - 20, height / 3), g2d.getFont());

        g2d.dispose();
    }

    // Helper method to draw centered text
    private void drawCenteredString(Graphics2D g2d, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }
}
