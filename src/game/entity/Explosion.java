package game.entity;

import game.BomberMan;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.GameFrame;
import game.ui.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static game.ui.GameFrame.GRID_SIZE;
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

    /**
     * Constructs a new explosion.
     *
     * @param coordinates The starting coordinates of the explosion.
     * @param direction The direction of the explosion.
     */
    public Explosion(Coordinates coordinates, Direction direction) {
        this(coordinates, direction, 0);
    }

    /**
     * Constructs a new explosion.
     *
     * @param coordinates The starting coordinates of the explosion.
     * @param direction The direction of the explosion.
     * @param distanceFromBomb The distance from the bomb where the explosion was created.
     */
    public Explosion(Coordinates coordinates, Direction direction, int distanceFromBomb) {
        super(coordinates);
        this.direction = direction;
        this.distanceFromBomb = distanceFromBomb;
        this.maxDistance = BomberMan.getInstance().getCurrentLevel().getExplosionLength();

        // Add the explosion entity to the game.
        BomberMan.getInstance().addEntity(this);

        // Move or interact with other entities in the game based on the explosion's direction.
        moveOrInteract(direction, getSize(), 30);
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
    }

    /**
     * Returns the size of the explosion.
     *
     * @return The size of the explosion.
     */
    @Override
    public int getSize() {
        return Utility.px(40);
    }

    /**
     * Sets the coordinates of the explosion and creates new explosions based on its distance from the bomb.
     *
     * @param coordinates The new coordinates of the explosion.
     */
    @Override
    public void setCoords(Coordinates coordinates) {
        if (distanceFromBomb <= maxDistance) {
            super.setCoords(getCoords());
            new Explosion(coordinates, direction, distanceFromBomb + 1);
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
        if (image != null) {
            return image;
        }

        if(distanceFromBomb == 0){
            return loadImage(String.format("%sflame_central.png", path));
        }

        String imageFileName;
        switch (direction) {
            case UP: case DOWN: imageFileName = "flame_vertical"; break;
            case RIGHT: case LEFT: imageFileName = "flame_horizontal"; break;
            default: return null;
        }

        // Choose a random image of the flame.
        String randomMirrored = Math.random() < 1f / 2f ? "_mirrored" : "";

        // Load and set the image of the flame.
        String imagePath = String.format("%s%s%s.png", path, imageFileName, randomMirrored);
        return loadAndSetImage(imagePath);
    }
}
