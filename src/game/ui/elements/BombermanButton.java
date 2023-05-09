package game.ui.elements;

import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;

public class BombermanButton extends JButton {
    private final int fontSize = Utility.px(60);
    private final int borderWidth = Utility.px(10);
    private final int cornerRadius = 15;
    private final Color backgroundColor = new Color(0, 0, 0, 0);
    private final Color textColor = new Color(255, 255, 255);
    private final Color borderColor = Color.ORANGE;
    private final Color shadowColor = new Color(0, 0, 0, 150);
    private final Color mouseHoverBackgroundColor = new Color(255, 102, 0);
    private boolean mouseEntered = false;

    public BombermanButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(textColor);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                mouseEntered = true;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                mouseEntered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - 1;
        int height = getHeight() - 1;
        int borderWidth = 5; // the size of the transparent border/margin

        // Draw background
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);

        // Draw text shadow
        if (mouseEntered) g2d.setColor(mouseHoverBackgroundColor);
        else g2d.setColor(borderColor);

        // Draw border
        g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        g2d.setColor(shadowColor);
        g2d.fillRoundRect(borderWidth, borderWidth, width - borderWidth * 2, height - borderWidth * 2, cornerRadius, cornerRadius);

        // Draw transparent border/margin
        g2d.setColor(new Color(0, 0, 0, 0)); // transparent color
        g2d.fillRect(0, 0, borderWidth, height); // left
        g2d.fillRect(width - borderWidth, 0, borderWidth, height); // right
        g2d.fillRect(borderWidth, 0, width - borderWidth * 2, borderWidth); // top
        g2d.fillRect(borderWidth, height - borderWidth, width - borderWidth * 2, borderWidth); // bottom

        // Draw text
        g2d.setColor(textColor);
        g2d.setFont(getFont());
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        String text = getText();
        Rectangle2D textBounds = g2d.getFontMetrics().getStringBounds(text, g2d);
        int textWidth = (int) textBounds.getWidth();
        int textHeight = (int) textBounds.getHeight();
        int textX = (width - textWidth) / 2;
        int textY = (height + textHeight / 2) / 2;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
    }

    @Override
    public void setBackground(Color bg) {
        // Ignore setBackground to keep the custom background color
    }

}