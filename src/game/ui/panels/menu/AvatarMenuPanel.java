package game.ui.panels.menu;

import game.entity.models.Direction;
import game.events.RunnablePar;
import game.localization.Localization;
import game.ui.helpers.Padding;
import game.ui.panels.models.JBombermanBoxContainerPanel;
import game.ui.viewelements.bombermanbutton.BombermanButton;
import game.ui.viewelements.bombermanbutton.RedButton;
import game.utils.Utility;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import static game.entity.enemies.npcs.IntelligentEnemy.DIRECTION_REFRESH_RATE;
import static game.localization.Localization.AVATAR;
import static game.values.Dimensions.DEFAULT_X_PADDING;
import static game.values.Dimensions.DEFAULT_Y_PADDING;

public class AvatarMenuPanel extends JBombermanBoxContainerPanel {
    private final Dimension imageDimension = new Dimension(Utility.px(75), Utility.px(75));
    private final Consumer<Integer> callback;
    private final RunnablePar getSkinRunnable;
    private JButton imageLabel;
    private int avatarIndex = 0;
    private long lastRefreshTime = 0;
    private int currDirectionIndex = 0;

    public AvatarMenuPanel(RunnablePar getSkinRunnable, Consumer<Integer> callback) {
        super(Localization.get(AVATAR), false);
        this.getSkinRunnable = getSkinRunnable;
        this.callback = callback;
        this.initializeLayout();
    }

    @Override
    public void initializeLayout() {
        this.imageLabel = new JButton();
        this.imageLabel.setOpaque(false);
        this.imageLabel.setBackground(new Color(0, 0, 0, 0));
        this.imageLabel.setBorderPainted(false);
        this.imageLabel.setContentAreaFilled(false);
        this.imageLabel.addActionListener(e -> {
            currDirectionIndex++;
            if (currDirectionIndex >= Direction.values().length) {
                currDirectionIndex = 0;
            }
            repaint();
        });
        super.initializeLayout();
    }

    @Override
    protected int getBoxPanelWidth() {
        return Utility.px(Dimensions.DEFAULT_MAIN_MENU_BOX_SIZE);
    }

    private String getAvatarImagesPath() {
        String currSkin = (String) getSkinRunnable.execute(null);
        String direction = Direction.values()[currDirectionIndex].toString().toLowerCase();

        String[] avatarImagesPath = new String[]{
                String.format("%s/player_%s_%d.png", currSkin, direction, 0),
                String.format("%s/player_%s_%d.png", currSkin, direction, 1),
                String.format("%s/player_%s_%d.png", currSkin, direction, 2),
                String.format("%s/player_%s_%d.png", currSkin, direction, 3),
        };


        avatarIndex++;

        if (avatarIndex >= avatarImagesPath.length)
            avatarIndex = 0;

        return avatarImagesPath[avatarIndex];
    }

    @Override
    protected void addCustomElements() {
        JPanel inlinePanel = new JPanel();
        inlinePanel.setOpaque(false);

        BombermanButton prevAvatarButton = new RedButton(" < ", Dimensions.FONT_SIZE_MID);
        prevAvatarButton.addActionListener(e -> {
            callback.accept(-1);
            repaint();
        });

        inlinePanel.add(prevAvatarButton);
        inlinePanel.add(new Padding(DEFAULT_X_PADDING, 0));

        updateImageAvatar();
        inlinePanel.add(imageLabel);

        inlinePanel.add(new Padding(DEFAULT_X_PADDING, 0));

        BombermanButton nextAvatarButton = new RedButton(" > ", Dimensions.FONT_SIZE_MID);
        nextAvatarButton.addActionListener(e -> {
            callback.accept(1);
            repaint();
        });

        inlinePanel.add(nextAvatarButton);

        componentsPanel.add(inlinePanel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (System.currentTimeMillis() - lastRefreshTime > DIRECTION_REFRESH_RATE)
            refresh();

        repaint();
    }

    private void updateImageAvatar() {
        lastRefreshTime = System.currentTimeMillis();

        Image image = Utility.loadImage(getAvatarImagesPath());
        assert image != null;

        image = image.getScaledInstance((int) imageDimension.getWidth(), (int) imageDimension.getHeight(), 0);
        imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setPreferredSize(imageDimension);
    }

    public void refresh() {
        updateImageAvatar();
    }
}
