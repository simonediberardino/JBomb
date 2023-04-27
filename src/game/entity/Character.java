package game.entity;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.GamePanel;

import java.awt.*;


/**
 * Represents a character in the game, which can move and interact with the environment.
 */
public abstract class Character extends InteractiveEntities {
    private Direction currDirection = null;
    /** The last direction this character was moving in. */
    private Direction previousDirection = null;

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
        return GamePanel.PIXEL_UNIT*5*2;
    }

    /**
     * Returns the image for this character based on its current state.
     *
     * @return the image for this character
     */
    @Override
    public Image getImage() {
        if(this.image == null){
            currDirection = Direction.DOWN;
            return loadAndSetImage(getFrontIcons()[0]);
        }else return this.image;
    }

    private void updateLastDirection(Direction d) {
        String[] frontIcons = getFrontIcons();
        String[] leftIcons = getLeftIcons();
        String[] backIcons = getBackIcons();
        String[] rightIcons = getRightIcons();

        if(previousDirection == null && currDirection == null){
            currDirection = d;
            loadAndSetImage(frontIcons[0]);
            return;
        }

        previousDirection = currDirection;
        currDirection = d;

        if (previousDirection != d) {
            lastImageIndex = 0;
        } else if(System.currentTimeMillis() - lastImageUpdate > imageRefreshRate){
            lastImageIndex++;
        }else {
            return;
        }

        String[] icons;
        switch (currDirection) {
            case LEFT: icons = leftIcons; break;
            case RIGHT: icons = rightIcons; break;
            case UP: icons = backIcons; break;
            default: icons = frontIcons; break;
        }


        // Ensure the icon index is within bounds
        if (lastImageIndex < 0 || lastImageIndex >= icons.length)
            lastImageIndex = 0;

        loadAndSetImage(icons[lastImageIndex]);
    }

    public void move(Direction d) {
        if(!getAliveState()) return;
        
        updateLastDirection(d);
        moveOrInteract(d);
    }
}
