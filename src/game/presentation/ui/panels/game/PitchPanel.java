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

    /**
     * Paints the graphics of the entities in the game world
     */

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g2d = (Graphics2D) g;

        Image img = Utility.INSTANCE.loadImage(JBomb.match.getCurrentLevel().getInfo().getPitchImagePath());
        g.drawImage(img.getScaledInstance((int) getMaximumSize().getWidth(), (int) getMaximumSize().getHeight(), 1), 0, 0, null);

        List<? extends Entity> setEntities = JBomb.match.getEntities();
        Player player = JBomb.match.getPlayer();

        setEntities.forEach(e -> {
            try {
                drawEntity(g2d, e);
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
            }
        });

        // consider only one for loop
        for (Entity e : setEntities) {
            if (e != player && e instanceof Character) {
                drawEntityLabel(g2d, (Character) e);
            }
        }

        if (player != null && !JBomb.match.isOnlyPlayer()) {
            drawEntityArrowhead(g2d, player);
        }

        // Runs custom callbacks;
        graphicsCallbacks.forEach((key, value) -> value.execute(g2d));
    }

    /**
     * Draws the "Player" label on the player's head inside a box.
     *
     * @param g2d the Graphics2D object to draw with
     * @param e   the entity to draw
     */
    private void drawEntityLabel(Graphics2D g2d, Character e) {
        String entityName = e.getProperties().getName();

        if (entityName == null || entityName.isEmpty())
            return;

        if (!e.getLogic().isAlive() || e.getState().getEliminated()) {
            return;
        }

        int x = e.getInfo().getPosition().getX();
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

    /**
     * Draws an entity on the game panel.
     *
     * @param g2d the Graphics2D object to draw with
     * @param e   the entity to draw
     */
    private void drawEntity(Graphics2D g2d, Entity e) {
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

        int x = e.getInfo().getPosition().getX();
        int y = e.getInfo().getPosition().getY() - paddingHeight;

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

    /**
     * Draws the arrowhead of the entity on the game panel.
     *
     * @param g2d the Graphics2D object to draw with
     * @param e   the entity to draw
     */
    private void drawEntityArrowhead(Graphics2D g2d, Entity e) {
        String path = e.getImage().getImagePath();
        float heightRatio = e.getGraphicsBehavior().getHitboxSizeToHeightRatio(e, path);
        int paddingHeight = e.getGraphicsBehavior().calculateAndGetPaddingTop(e, heightRatio);
        int x = e.getInfo().getPosition().getX();
        int y = e.getInfo().getPosition().getY() - paddingHeight;
        int size = (int) (double) e.getState().getSize();

        Polygon arrowhead = new Polygon();
        arrowhead.addPoint(x + size / 2, y);
        arrowhead.addPoint(x + size / 3, y - size*3/4);
        arrowhead.addPoint(x + size* 2 / 3, y - size * 3/4);

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