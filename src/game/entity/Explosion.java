package game.entity;

import game.BomberMan;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.GameFrame;
import game.ui.GamePanel;
import game.ui.Utility;

import java.awt.*;
import java.sql.Time;

import static game.ui.Utility.loadImage;

/**
 * Represents an explosion that can interact with other entities in the game.
 */
public class Explosion extends InteractiveEntities {
    // The distance from the bomb where the explosion was created.
    private final int distanceFromBomb;

    // The maximum distance from the bomb that the explosion can travel.
    private final int maxDistance;

    // The direction of the explosion.
    public final Direction direction;
    private boolean appearing = true;
    private int explosionState = 1;
    private long lastRefresh = 0;
    private final int refreshRate = 100;
    private final Bomb bomb;

    /**
     * Constructs a new explosion.
     *
     * @param coordinates The starting coordinates of the explosion.
     * @param direction The direction of the explosion.
     */
    public Explosion(Coordinates coordinates, Direction direction,Bomb bomb) {
        this(coordinates, direction, 0, bomb);
    }

    /**
     * Constructs a new explosion.
     *
     * @param coordinates The starting coordinates of the explosion.
     * @param direction The direction of the explosion.
     * @param distanceFromBomb The distance from the bomb where the explosion was created.
     */
    public Explosion(Coordinates coordinates, Direction direction, int distanceFromBomb,Bomb bomb) {
        super(coordinates);
        this.direction = direction;
        this.distanceFromBomb = distanceFromBomb;
        this.maxDistance = BomberMan.getInstance().getCurrentLevel().getExplosionLength();
        this.bomb = bomb;

        // Add the explosion entity to the game.
        BomberMan.getInstance().addEntity(this);

        // Move or interact with other entities in the game based on the explosion's direction.
        moveOrInteract(direction, getSize(), getSize()/2);
    }

    /**
     * Interacts with another entity in the game.
     *
     * @param e The entity to interact with.
     */
    @Override
    public void interact(Entity e) {
        if (e instanceof Character) {
            e.despawn();
        }
        if (e instanceof Bomb){
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
        return GamePanel.COMMON_DIVISOR*4;
    }

    /**
     * Sets the coordinates of the explosion and creates new explosions based on its distance from the bomb.
     *
     * @param coordinates The new coordinates of the explosion.
     */
    @Override
    public void setCoords(Coordinates coordinates) {
        if (distanceFromBomb < maxDistance) {
            bomb.addExplosion(this);

            new Explosion(coordinates, direction, distanceFromBomb + 1,bomb);
        }
    }

    /**
     * Returns the image of the explosion.
     *
     * @return The image of the explosion.
     */
    @Override
    public Image getImage() {
        String path = "assets/Bomb/";

        if(distanceFromBomb == 0){
            return loadImage(String.format("%sflame_central" + getState() + ".png", path));
        }

        String imageFileName;
        String isLast = "";
        if (distanceFromBomb >= maxDistance) {
            isLast = "_last";
        }
        imageFileName = "flame_" + direction.toString();

        // Load and set the image of the flame.
        String imagePath = String.format("%s%s%s%s.png", path, imageFileName,isLast, getState());
        return loadAndSetImage(imagePath);
    }

    public int getState(){
        if (explosionState == 0 && !appearing){
            despawn();
            appearing = true;
            return 0;
        }

        if (explosionState == 3){
            appearing = false;
        }

        int appearingConstant = !appearing ? -1 : 1;

        int prevState = explosionState;
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastRefresh >= refreshRate ) {
            explosionState += appearingConstant;
            lastRefresh = currentTime;
        }

        return prevState;
    }



}
