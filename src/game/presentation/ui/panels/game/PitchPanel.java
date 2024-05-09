package game.presentation.ui.panels.game;

import game.Bomberman;
import game.audio.AudioManager;
import game.domain.events.models.RunnablePar;
import game.domain.match.BomberManMatch;
import game.domain.tasks.observer.Observable2;
import game.domain.tasks.observer.Observer2;
import game.domain.world.domain.entity.actors.abstracts.base.Entity;
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss;
import game.utils.Utility;
import game.utils.dev.Log;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import static game.audio.SoundModel.LIGHT_GLITCH;

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
        Bomberman.match.getGameTickerObservable().unregisterAll();
        Bomberman.match.getGameTickerObservable().register(this);

        repaint();
    }

    public static void turnOffLights() {
        BomberManMatch match = Bomberman.match;
        if (match == null || !match.getGameState()) return;

        PitchPanel pitchPanel = Bomberman.bombermanFrame.getPitchPanel();
        AudioManager.getInstance().play(LIGHT_GLITCH);

        pitchPanel.addGraphicsCallback(
                GhostBoss.class.getSimpleName(), new RunnablePar() {
                    @Override
                    public <T> Object execute(T par) {
                        Graphics2D g2d = Bomberman.bombermanFrame.getPitchPanel().g2d;
                        g2d.setColor(new Color(0, 0, 0, 0.9f));
                        g2d.fillRect(0, 0, Bomberman.bombermanFrame.getHeight(), Bomberman.bombermanFrame.getWidth());
                        return null;
                    }
                }
        );
    }

    public static void turnOnLights() {
        BomberManMatch match = Bomberman.match;
        if (match == null || !match.getGameState()) return;

        AudioManager.getInstance().play(LIGHT_GLITCH);
        PitchPanel pitchPanel = Bomberman.bombermanFrame.getPitchPanel();
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

        Image img = Utility.INSTANCE.loadImage(Bomberman.match.getCurrentLevel().getInfo().getPitchImagePath());
        g.drawImage(img.getScaledInstance((int) getMaximumSize().getWidth(), (int) getMaximumSize().getHeight(), 1), 0, 0, null);

        List<? extends Entity> setEntities = Bomberman.match.getEntities();

        setEntities.forEach(e -> {
            try {
                drawEntity(g2d, e);
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
            }
        });
        // Runs custom callbacks;
        graphicsCallbacks.forEach((key, value) -> value.execute(g2d));
    }

    /**
     * Draws an entity on the game panel
     *
     * @param g2d the Graphics2D object to draw with
     * @param e   the entity to draw
     */
    private void drawEntity(Graphics2D g2d, Entity e) {
        // Draw entity's image at entity's coordinates and size
        if (e.getState().isInvisible())
            return;

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

        g2d.drawImage(
                e.getGraphicsBehavior().getImage(e),
                e.getInfo().getPosition().getX() - paddingWidth,
                e.getInfo().getPosition().getY() - paddingHeight,
                (int) Math.ceil(e.getState().getSize() / widthRatio),
                (int) Math.ceil(e.getState().getSize() / heightRatio),
                this
        );
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

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