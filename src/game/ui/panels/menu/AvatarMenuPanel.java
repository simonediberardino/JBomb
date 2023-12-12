package game.ui.panels.menu;

import game.events.RunnablePar;
import game.localization.Localization;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static game.entity.enemies.npcs.IntelligentEnemy.DIRECTION_REFRESH_RATE;
import static game.localization.Localization.AVATAR;

public class AvatarMenuPanel extends JBombermanBoxContainerPanel {
    private final Consumer<Integer> callback;
    private final RunnablePar getSkinRunnable;
    private final String[] avatarPaths;
    private JLabel imageLabel;
    private int avatarIndex = 0;
    private long lastRefreshTime = 0;

    public AvatarMenuPanel(String[] avatarPaths, RunnablePar getSkinRunnable, Consumer<Integer> callback) {
        super(Localization.get(AVATAR), false);
        this.avatarPaths = avatarPaths;
        this.getSkinRunnable = getSkinRunnable;
        this.callback = callback;
        initializeLayout();
    }

    @Override
    int getBoxPanelWidth() {
        return Utility.px(500);
    }

    private String getAvatarImagesPath() {
        String currSkin = (String) getSkinRunnable.execute(null);
        String direction = "down";

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
        imageLabel = addImageLabel(getAvatarImagesPath());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (System.currentTimeMillis() - lastRefreshTime > DIRECTION_REFRESH_RATE)
            refresh();

        repaint();
    }


    public void refresh() {
        lastRefreshTime = System.currentTimeMillis();
        Image image = Utility.loadImage(getAvatarImagesPath());

        assert image != null;
        imageLabel.setIcon(new ImageIcon(image));
    }
}
