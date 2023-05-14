package game.entity.bomb;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.models.Particle;
import game.entity.blocks.DestroyableBlock;
import game.entity.models.*;
import game.models.Coordinates;
import game.models.Direction;
import game.utils.Paths;
import game.ui.panels.game.PitchPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static game.utils.Utility.loadImage;

/**
 * Represents an explosion that can interact with other entities in the game.
 */
public class Explosion extends MovingEntity implements Particle {
    public static final int SIZE = PitchPanel.COMMON_DIVISOR * 2;
    // The distance from the bomb where the explosion was created.
    public final int distanceFromExplosive;

    // The maximum distance from the bomb that the explosion can travel.
    private final int maxDistance;
    private static final int BOMB_STATES = 3;
    private boolean canExpand;

    // The direction of the explosion.
    public final Direction direction;
    private boolean appearing = true;
    private int explosionState = 1;
    private long lastRefresh = 0;
    private final Explosive explosive;
    public static final int spawnOffset = (PitchPanel.GRID_SIZE-SIZE)/2;


    public Explosion(Coordinates coordinates, Direction direction, Explosive explosive) {
        this(coordinates, direction, 0, explosive);
    }

    public Explosion(Coordinates coordinates, Direction direction, int distanceFromExplosive, Explosive explosive) {
        this(coordinates, direction, distanceFromExplosive, explosive, true);

    }

    /**
     * Constructs a new explosion.
     *
     * @param coordinates           The starting coordinates of the explosion.
     * @param direction             The direction of the explosion.
     * @param distanceFromExplosive The distance from the bomb where the explosion was created.
     */
    public Explosion(Coordinates coordinates, Direction direction, int distanceFromExplosive, Explosive explosive, boolean canExpand) {
        super(coordinates);

        this.direction = direction;
        this.distanceFromExplosive = distanceFromExplosive;

        this.explosive = explosive;
        this.maxDistance = explosive.getMaxExplosionDistance();
        this.canExpand = canExpand;

        // Add the explosion entity to the game.
        Bomberman.getMatch().addEntity(this);

        // Move or interact with other entities in the game based on the explosion's direction.
        if (distanceFromExplosive == 0){
            List<Coordinates> desiredCoords = getAllCoordinates();
            for (Entity e : Bomberman.getMatch().getEntities()) {
                if (desiredCoords.stream().anyMatch(coord -> EntityInteractable.doesCollideWith(coord, e))){
                    interact(e);
                }
            }

        }

        SwingUtilities.invokeLater(() -> {
            if (getCanExpand())
                moveOrInteract(direction, getSize(), true);
        });
    }

    @Override
    protected String getBasePath() {
        return Paths.getAssetsFolder() + "/bomb/";
    }

    /**
     * Interacts with another entity in the game.
     *
     * @param e The entity to interact with.
     */
    @Override
    protected void doInteract(Entity e) {
        if (e instanceof BomberEntity || e instanceof Enemy || e instanceof DestroyableBlock) {
            attack(e);
        } else if (e instanceof Bomb) {
            ((Bomb) e).explode();
        }
    }


    /**
     * Returns the size of the explosion.
     *
     * @return The size of the explosion.
     */
    @Override
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the coordinates of the explosion and creates new explosions based on its distance from the bomb.
     *
     * @param coordinates The new coordinates of the explosion.
     */
    @Override
    public void setCoords(Coordinates coordinates) {
        if (canExpand) {
            super.setCoords(getCoords());

            new Explosion(coordinates, direction, distanceFromExplosive + 1, explosive);
        }
    }

    /**
     * Returns the image of the explosion.
     *
     * @return The image of the explosion.
     */
    @Override
    public BufferedImage getImage() {
        if (distanceFromExplosive == 0) {
            return loadImage(String.format("%sflame_central" + getState() + ".png", getBasePath()));
        }

        String imageFileName;
        String isLast = "";
        if (!canExpand) {
            isLast = "_last";
        }
        imageFileName = "flame_" + direction.toString().toLowerCase();

        // Load and set the image of the flame.
        String imagePath = String.format("%s%s%s%s.png", getBasePath(), imageFileName, isLast, getState());
        return loadAndSetImage(imagePath);
    }

    public int getState() {
        if (explosionState == 0 && !appearing) {
            despawn();
            appearing = true;
            return 0;
        }

        if (explosionState == BOMB_STATES) appearing = false;

        int appearingConstant = !appearing ? -1 : 1;

        int prevState = explosionState;
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastRefresh >= getImageRefreshRate()) {
            explosionState += appearingConstant;
            lastRefresh = currentTime;
        }

        return prevState;
    }

    @Override
    public int getImageRefreshRate() {
        return 100;
    }

    @Override
    public boolean isObstacle(Entity e) {
        return (e == null) || getExplosive().isObstacleOfExplosion(e);
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return new HashSet<>(getExplosive().getExplosionObstacles());
    }
    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities(){
         return new HashSet<>(getExplosive().getExplosionInteractionEntities());
    };

    public boolean getCanExpand() {
        if (distanceFromExplosive >= maxDistance) canExpand = false;
        return canExpand;
    }

    public void cantExpandAnymore() {
        Coordinates nextTopLeftCoords = nextCoords(direction, getSize());
        new Explosion(nextTopLeftCoords, direction, distanceFromExplosive + 1, getExplosive(), false);
    }

    public Explosive getExplosive() {
        return explosive;
    }
}
