package game.presentation.ui.panels.game;

import game.JBomb;
import game.audio.AudioManager;
import game.domain.events.models.RunnablePar;
import game.domain.match.JBombMatch;
import game.domain.tasks.observer.Observable2;
import game.domain.tasks.observer.Observer2;
import game.domain.world.domain.entity.actors.abstracts.base.Entity;
import game.domain.world.domain.entity.actors.abstracts.character.Character;
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player;
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss;
import game.domain.world.domain.entity.actors.impl.models.State;
import game.domain.world.domain.entity.geo.Coordinates;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.utils.Utility;
import game.utils.dev.Log;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import static game.audio.SoundModel.LIGHT_GLITCH;
import static game.values.Dimensions.FONT_SIZE_LITTLE;

/**
 * The GamePanel class represents the main game panel that displays the game world and entities
 */
public class PitchPanel extends JPanel implements Observer2 {

    public static final int DEFAULT_PIXEL_UNIT = 6;
    public static final int PIXEL_UNIT = Utility.INSTANCE.px(DEFAULT_PIXEL_UNIT);
    //to simplify calculations
    public static final int COMMON_DIVISOR = PIXEL_UNIT * 4;
    public static final int GRID_SIZE = PitchPanel.COMMON_DIVISOR * 3;
    //GRID_SIZE must be multiplied by an odd number in order to guarantee free space around borders on the game pitch
    public static final Dimension DIMENSION = new Dimension(GRID_SIZE * 13, 11 * GRID_SIZE);
    private final HashMap<String, RunnablePar> graphicsCallbacks = new HashMap<>();
    public volatile Graphics2D g2d;
    private static final int CAMERA_LAG_DISTANCE = 50; // Pixels before camera starts moving
    private static final float CAMERA_SMOOTHING_FACTOR = 0.1f; // Smoothing factor (0 < x < 1)

    /**
     * Constructs a new GamePanel with the default dimensions and sets it as the observer for the game ticker observable
     */
    public PitchPanel() {
        // Set preferred, maximum, and minimum size to converted dimensions
        setPreferredSize(DIMENSION);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);

        // Set this GamePanel as observer for the game ticker observable
        JBomb.match.getGameTickerObservable().unregisterAll();
        JBomb.match.getGameTickerObservable().register(this);

        repaint();
    }

    public static void turnOffLights() {
        JBombMatch match = JBomb.match;
        if (match == null || !match.getGameState()) return;

        PitchPanel pitchPanel = JBomb.JBombFrame.getPitchPanel();
        AudioManager.getInstance().play(LIGHT_GLITCH);

        pitchPanel.addGraphicsCallback(
                GhostBoss.class.getSimpleName(), new RunnablePar() {
                    @Override
                    public <T> Object execute(T par) {
                        Graphics2D g2d = JBomb.JBombFrame.getPitchPanel().g2d;
                        g2d.setColor(new Color(0, 0, 0, 0.9f));
                        g2d.fillRect(0, 0, JBomb.JBombFrame.getHeight(), JBomb.JBombFrame.getWidth());
                        return null;
                    }
                }
        );
    }

    public static void turnOnLights() {
        JBombMatch match = JBomb.match;
        if (match == null || !match.getGameState()) return;

        AudioManager.getInstance().play(LIGHT_GLITCH);
        PitchPanel pitchPanel = JBomb.JBombFrame.getPitchPanel();
        pitchPanel.removeGraphicsCallback(GhostBoss.class.getSimpleName());
    }

    public Dimension getPanelDimensions() {
        return DIMENSION;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g2d = (Graphics2D) g;

        Dimension pitchPanelSize = new Dimension(getWidth(), getHeight());
        Dimension mapDimensions = Coordinates.getMapDimensions();

        // Load the background image
        Image img = Utility.INSTANCE.loadImage(JBomb.match.getCurrentLevel().getInfo().getPitchImagePath());

        // Calculate camera offset based on player position
        Player player = JBomb.match.getPlayer();
        int cameraOffsetX = 0;
        int cameraOffsetY = 0;

        if (player != null) {
            cameraOffsetX = player.getInfo().getPosition().getX() - (pitchPanelSize.width / 2);
            cameraOffsetY = player.getInfo().getPosition().getY() - (pitchPanelSize.height / 2);

            // Clamp the camera offsets to ensure they don't exceed the game world boundaries
            cameraOffsetX = Math.max(0, Math.min(cameraOffsetX, mapDimensions.width - pitchPanelSize.width));
            cameraOffsetY = Math.max(0, Math.min(cameraOffsetY, mapDimensions.height - pitchPanelSize.height));
        }

        // Calculate the dimensions of the background image
        int imgWidth = pitchPanelSize.width;
        int imgHeight = pitchPanelSize.height;

        // Calculate the starting position for the background
        int startX = -cameraOffsetX % imgWidth;
        int startY = -cameraOffsetY % imgHeight;

        // Draw the background tiles to fit the pitch panel dimensions
        for (int x = startX; x < pitchPanelSize.width; x += imgWidth) {
            for (int y = startY; y < pitchPanelSize.height; y += imgHeight) {
                g.drawImage(img, x, y, pitchPanelSize.width, pitchPanelSize.height, null);
            }
        }

        // Draw the entities
        List<? extends Entity> setEntities = JBomb.match.getEntities();
        int finalCameraOffsetX = cameraOffsetX;
        int finalCameraOffsetY = cameraOffsetY;
        setEntities.forEach(e -> {
            try {
                drawEntity(g2d, e, finalCameraOffsetX, finalCameraOffsetY); // Pass offsets to drawEntity
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
            }
        });

        // Draw labels for other entities
        for (Entity e : setEntities) {
            if (e != player && e instanceof Character) {
                drawEntityLabel(g2d, (Character) e, cameraOffsetX, cameraOffsetY); // Pass offsets to drawEntityLabel
            }
        }

        if (!JBomb.match.isOnlyPlayer() && player != null && player.getLogic().isAlive()) {
            drawEntityArrowhead(g2d, player, cameraOffsetX, cameraOffsetY); // Pass offsets to drawEntityArrowhead
        }

        // Runs custom callbacks;
        graphicsCallbacks.forEach((key, value) -> value.execute(g2d));
    }




    private void drawEntityLabel(Graphics2D g2d, Character e, int cameraOffsetX, int cameraOffsetY) {
        String entityName = e.getProperties().getName();

        if (entityName == null || entityName.isEmpty())
            return;

        if (!e.getLogic().isAlive() || e.getState().getEliminated()) {
            return;
        }

        int x = e.getInfo().getPosition().getX() - cameraOffsetX; // Apply camera offset
        int y = e.getInfo().getPosition().getY();
        int size = (int) (double) e.getState().getSize();

        YellowButton playerButton = new YellowButton(entityName, FONT_SIZE_LITTLE);

        // Set button size
        Dimension buttonSize = playerButton.getPreferredSize();
        playerButton.setSize(buttonSize);

        int centerX = x + e.getState().getSize() / 2;
        // Position the button (slightly down from the head and centered)
        int buttonX = centerX - buttonSize.width / 2;
        int buttonY = y - size / 2 - buttonSize.height;

        // Draw the button on the Graphics2D context
        SwingUtilities.paintComponent(g2d, playerButton, this, buttonX, buttonY, buttonSize.width, buttonSize.height);
    }


    private void drawEntity(Graphics2D g2d, Entity e, int cameraOffsetX, int cameraOffsetY) {
        if (e.getState().isInvisible()) {
            return;
        }
        String path = e.getImage().getImagePath();
        float widthRatio = e.getGraphicsBehavior().getHitboxSizeToWidthRatio(e, path);
        float heightRatio = e.getGraphicsBehavior().getHitboxSizeToHeightRatio(e, path);
        int paddingWidth = e.getGraphicsBehavior().calculateAndGetPaddingWidth(e, widthRatio);
        int paddingHeight = e.getGraphicsBehavior().calculateAndGetPaddingTop(e, heightRatio);

        try {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, e.getState().getAlpha());
            g2d.setComposite(ac);
        } catch (Exception exception) {
            Log.INSTANCE.e("Alpha value error " + e.getState().getAlpha());
            exception.printStackTrace();
        }

        int x = e.getInfo().getPosition().getX() - cameraOffsetX; // Apply camera offset
        int y = e.getInfo().getPosition().getY() - paddingHeight - cameraOffsetY; // Apply camera offset

        g2d.drawImage(
                e.getGraphicsBehavior().getImage(e),
                x - paddingWidth,
                y,
                (int) Math.ceil(e.getState().getSize() / widthRatio),
                (int) Math.ceil(e.getState().getSize() / heightRatio),
                this
        );

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }


    private void drawEntityArrowhead(Graphics2D g2d, Entity e, int cameraOffsetX, int cameraOffsetY) {
        String path = e.getImage().getImagePath();
        float heightRatio = e.getGraphicsBehavior().getHitboxSizeToHeightRatio(e, path);
        int paddingHeight = e.getGraphicsBehavior().calculateAndGetPaddingTop(e, heightRatio);
        int x = e.getInfo().getPosition().getX() - cameraOffsetX; // Apply camera offset
        int y = e.getInfo().getPosition().getY() - paddingHeight - cameraOffsetY; // Apply camera offset
        int size = (int) (double) e.getState().getSize();

        Polygon arrowhead = new Polygon();
        arrowhead.addPoint(x + size / 2, y);
        arrowhead.addPoint(x + size / 3, y - size * 3 / 4);
        arrowhead.addPoint(x + size * 2 / 3, y - size * 3 / 4);

        GradientPaint gradient = new GradientPaint(
                x, y + (float) size / 4, Color.RED,
                x + size, y + (float) size / 4, Color.ORANGE
        );
        g2d.setPaint(gradient);
        g2d.fill(arrowhead);

        g2d.setColor(new Color(0, 0, 0, 50)); // Transparent black
        g2d.translate(2, 2); // Shift for shadow effect
        g2d.fill(arrowhead); // Draw shadow
        g2d.translate(-2, -2); // Reset translation
    }


    public void addGraphicsCallback(String tag, RunnablePar callback) {
        graphicsCallbacks.put(tag, callback);
    }

    public void removeGraphicsCallback(String tag) {
        graphicsCallbacks.remove(tag);
    }

    public void clearGraphicsCallback() {
        graphicsCallbacks.clear();
    }

    /**
     * Repaints the game panel when it is updated by the game ticker observable
     *
     * @param arg  the object argument
     */
    @Override
    public void update(@NotNull Observable2.ObserverParam arg) {
        repaint();
    }
}