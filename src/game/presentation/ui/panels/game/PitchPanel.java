package game.presentation.ui.panels.game;

import game.JBomb;
import game.domain.events.models.RunnablePar;
import game.domain.level.levels.Level;
import game.domain.tasks.observer.Observable2;
import game.domain.tasks.observer.Observer2;
import game.domain.world.domain.entity.actors.abstracts.base.Entity;
import game.domain.world.domain.entity.actors.abstracts.character.Character;
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player;
import game.domain.world.domain.entity.geo.Coordinates;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.properties.RuntimeProperties;
import game.utils.Utility;
import game.utils.dev.Log;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * Constructs a new GamePanel with the default dimensions and sets it as the observer for the game ticker observable
     */
    public PitchPanel() {
        // Set preferred, maximum, and minimum size to converted dimensions
        setPreferredSize(DIMENSION);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);

        repaint();
    }

    public Dimension getPanelDimensions() {
        return DIMENSION;
    }

    private int cameraOffsetX;
    private int cameraOffsetY;

    // Create an executor service for parallel computation
    private final ExecutorService executor = Executors.newFixedThreadPool(RuntimeProperties.INSTANCE.getProcessors());

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
        Level level = JBomb.match.getCurrentLevel();
        boolean cameraMoveEnabled = level.getInfo().getCameraMoveEnabled();

        if (player == null) {
            cameraMoveEnabled = false; // do not move the camera, remember last position
        } else {
            cameraOffsetX = 0;
            cameraOffsetY = 0;
        }

        if (cameraMoveEnabled) {
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

        // Get the list of entities
        List<? extends Entity> setEntities = JBomb.match.getEntities();
        int finalCameraOffsetX = cameraOffsetX;
        int finalCameraOffsetY = cameraOffsetY;

        // Define the visible area (screen bounds in world coordinates)
        int visibleAreaX1 = cameraOffsetX;
        int visibleAreaY1 = cameraOffsetY;
        int visibleAreaX2 = cameraOffsetX + pitchPanelSize.width;
        int visibleAreaY2 = cameraOffsetY + pitchPanelSize.height;

        // Store the preprocessed data for each entity
        List<CompletableFuture<EntityDrawData>> futures = new ArrayList<>();

        // Preprocess entity data (e.g., calculate drawing positions) in parallel
        for (Entity e : setEntities) {
            CompletableFuture<EntityDrawData> future = CompletableFuture.supplyAsync(() -> {
                // Compute the drawing data for this entity in parallel (position, transformation, etc.)
                try {
                    int entityX = e.getInfo().getPosition().getX();
                    int entityY = e.getInfo().getPosition().getY();
                    int entitySize = e.getState().getSize();

                    Image image = e.getGraphicsBehavior().getImage(e);

                    // Check if entity is within the visible area (partially or fully)
                    if (entityX + entitySize >= visibleAreaX1 && entityX <= visibleAreaX2 &&
                            entityY + entitySize >= visibleAreaY1 && entityY <= visibleAreaY2) {
                        return new EntityDrawData(g2d, e, finalCameraOffsetX, finalCameraOffsetY, image);
                    }
                    return null;
                } catch (ConcurrentModificationException ex) {
                    return null;
                }
            }, executor);
            futures.add(future);
        }

        // Draw the entities sequentially in the correct order
        for (CompletableFuture<EntityDrawData> future : futures) {
            if (future == null) continue;

            EntityDrawData drawData = future.join();

            if (drawData == null || drawData.getEntity() == null)
                continue;

            drawEntity(drawData);
        }

        // Draw labels for other entities
        for (Entity e : setEntities) {
            if (e != player && e instanceof Character) {
                drawEntityLabel(g2d, (Character) e, cameraOffsetX, cameraOffsetY); // Pass offsets to drawEntityLabel
            }
        }

        if (player != null && player.getLogic().isAlive()) {
            drawEntityArrowhead(
                    g2d,
                    player,
                    cameraOffsetX,
                    cameraOffsetY
            ); // Pass offsets to drawEntityArrowhead
        }

        // Runs custom callbacks
        graphicsCallbacks.forEach((key, value) -> value.execute(g2d));
    }

    public int getCameraOffsetX() {
        return cameraOffsetX;
    }

    public int getCameraOffsetY() {
        return cameraOffsetY;
    }

    private void drawEntityLabel(Graphics2D g2d, Character e, int cameraOffsetX, int cameraOffsetY) {
        String entityName = e.getProperties().getName();

        if (entityName == null || entityName.isEmpty())
            return;

        if (!e.getLogic().isAlive() || e.getState().getEliminated()) {
            return;
        }

        int x = e.getInfo().getPosition().getX() - cameraOffsetX; // Apply camera offset
        int y = e.getInfo().getPosition().getY() - cameraOffsetY;
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

    private void drawEntity(
            EntityDrawData entityDrawData
    ) {
        if (entityDrawData.getEntity().getState().isInvisible()) {
            return;
        }
        String path = entityDrawData.getEntity().getImage().getImagePath();
        float widthRatio = entityDrawData.getEntity().getGraphicsBehavior().getHitboxSizeToWidthRatio(entityDrawData.getEntity(), path);
        float heightRatio = entityDrawData.getEntity().getGraphicsBehavior().getHitboxSizeToHeightRatio(entityDrawData.getEntity(), path);
        int paddingWidth = entityDrawData.getEntity().getGraphicsBehavior().calculateAndGetPaddingWidth(entityDrawData.getEntity(), widthRatio);
        int paddingHeight = entityDrawData.getEntity().getGraphicsBehavior().calculateAndGetPaddingTop(entityDrawData.getEntity(), heightRatio);

        try {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, entityDrawData.getEntity().getState().getAlpha());
            g2d.setComposite(ac);
        } catch (Exception exception) {
            Log.INSTANCE.e("Alpha value error " + entityDrawData.getEntity().getState().getAlpha());
            exception.printStackTrace();
        }

        int x = entityDrawData.getEntity().getInfo().getPosition().getX() - cameraOffsetX; // Apply camera offset
        int y = entityDrawData.getEntity().getInfo().getPosition().getY() - paddingHeight - cameraOffsetY; // Apply camera offset

        g2d.drawImage(
                entityDrawData.getEntityImage(),
                x - paddingWidth,
                y,
                (int) Math.ceil(entityDrawData.getEntity().getState().getSize() / widthRatio),
                (int) Math.ceil(entityDrawData.getEntity().getState().getSize() / heightRatio),
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

    static class EntityDrawData {
        private final Graphics2D g2d;
        private final Entity e;
        private final int cameraOffsetX;
        private final int cameraOffsetY;
        private final Image entityImage;

        public EntityDrawData(
                Graphics2D g2d,
                Entity e,
                int cameraOffsetX,
                int cameraOffsetY,
                Image entityImage
        ) {
            this.g2d = g2d;
            this.e = e;
            this.cameraOffsetX = cameraOffsetX;
            this.cameraOffsetY = cameraOffsetY;
            this.entityImage = entityImage;
        }

        public Graphics2D getG2d() {
            return g2d;
        }

        public Entity getEntity() {
            return e;
        }

        public int getCameraOffsetX() {
            return cameraOffsetX;
        }

        public int getCameraOffsetY() {
            return cameraOffsetY;
        }

        public Image getEntityImage() {
            return entityImage;
        }
    }


}
