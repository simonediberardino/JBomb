package game.entity;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static game.ui.Utility.loadImage;



/**
 * Represents a character in the game, which can move and interact with the environment.
 */
public abstract class Character extends InteractiveEntities {
    /** The number of pixels per unit for this character. */
    public final static int PIXEL_UNIT = Utility.px(10);

    /** The index of the current position icon. */
    private int currentPositionIconIndex = 0;

    /** The last direction this character was moving in. */
    private Direction lastDirection = Direction.DOWN;

    /** Whether this character is alive or not. */
    protected boolean isAlive = true;

    /**
     * Returns an array of file names for the front-facing icons for this character.
     *
     * @return an array of file names for the front-facing icons
     */
    public abstract String[] getFrontIcons();

    /**
     * Returns an array of file names for the left-facing icons for this character.
     *
     * @return an array of file names for the left-facing icons
     */
    public abstract String[] getLeftIcons();

    /**
     * Returns an array of file names for the back-facing icons for this character.
     *
     * @return an array of file names for the back-facing icons
     */
    public abstract String[] getBackIcons();

    /**
     * Returns an array of file names for the right-facing icons for this character.
     *
     * @return an array of file names for the right-facing icons
     */
    public abstract String[] getRightIcons();

    /**
     * Constructs a new Character with the specified Coordinates.
     *
     * @param coordinates the coordinates of the new Character
     */
    public Character(Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Returns whether this character is alive or not.
     *
     * @return true if this character is alive, false otherwise
     */
    public boolean getAliveState() {
        return isAlive;
    }

    /**
     * Returns the size of this character.
     *
     * @return the size of this character
     */
    @Override
    public int getSize() {
        return Utility.px(50);
    }

    /**
     * Returns the image for this character based on its current state.
     *
     * @return the image for this character
     */
    @Override
    public Image getImage() {
        String[] frontIcons = getFrontIcons();
        String[] leftIcons = getLeftIcons();
        String[] backIcons = getBackIcons();
        String[] rightIcons = getRightIcons();

        // If last direction is null, use the first front-facing icon
        if (lastDirection == null) {
            try {
                return ImageIO.read(new File(frontIcons[0]));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        String[] icons;
        switch (lastDirection) {
            case LEFT:
                icons = leftIcons;
                break;
            case RIGHT:
                icons = rightIcons;
                break;
            case UP:
                icons = backIcons;
                break;
            default:
                icons = frontIcons;
                break;
        }

        // Ensure the icon index is within bounds
        if (currentPositionIconIndex < 0 || currentPositionIconIndex >= icons.length)
            currentPositionIconIndex = 0;

        String icon = icons[currentPositionIconIndex];
        return loadAndSetImage(icon);
    }


    private void updateLastDirection(Direction d) {
        if (lastDirection != d) {
            currentPositionIconIndex = 0;
        } else {
            currentPositionIconIndex++;
        }

        lastDirection = d;
    }

    public void move(Direction d) {
        if(!getAliveState()) return;
        
        updateLastDirection(d);
        moveOrInteract(d);
    }

    public Direction getLastDirection() {
        return lastDirection;
    }
}
