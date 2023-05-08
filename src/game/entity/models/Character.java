package game.entity.models;

import game.BomberMan;
import game.controller.Command;
import game.controller.ControllerManager;
import game.models.Coordinates;
import game.models.Direction;
import game.panels.PitchPanel;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

import static game.models.Direction.*;
import static game.models.Direction.DOWN;


/**
 * Represents a character in the game, which can move and interact with the environment.
 */
public abstract class Character extends EntityDamage {
    public static final int PADDING_HEAD = PitchPanel.PIXEL_UNIT*4;
    public static final int SIZE = PitchPanel.PIXEL_UNIT * 4 * 2;
    protected long lastDirectionUpdate = 0;
    protected Direction currDirection = Direction.values()[(int) (Math.random()* values().length)];
    /** The last direction this character was moving in. */
    protected Direction previousDirection = null;
    private int healthPoints = 100;

    /** Whether this character is alive or not. */
    protected boolean isAlive = true;
    protected boolean isImmune = false;

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
        return SIZE;
    }
    /**
     * Returns the image for this character based on its current state.
     *
     * @return the image for this character
     */

    @Override
    public BufferedImage getImage() {
        if (this.image != null) {
            return this.image;
        } else {
            currDirection = Direction.DOWN;
            return loadAndSetImage(getFrontIcons()[0]);
        }
    }

    public boolean isImmune() {
        return isImmune;
    }

    public void setImmune(boolean immune) {
        isImmune = immune;
    }

    /**
     * Updates the last direction the entity moved in and sets the appropriate image based on the direction.
     * @param d The new direction the entity is moving in.
     */
    protected void updateLastDirection(Direction d) {
        String[] frontIcons = getFrontIcons();
        String[] leftIcons = getLeftIcons();
        String[] backIcons = getBackIcons();
        String[] rightIcons = getRightIcons();

        // If both previousDirection and currDirection are null, initialize currDirection to d and load the front-facing image.
        if(previousDirection == null && currDirection == null){
            currDirection = d;
            loadAndSetImage(frontIcons[0]);
            return;
        }

        // Update previousDirection and currDirection to reflect the new direction.
        previousDirection = currDirection;
        currDirection = d;

        // If the previousDirection and current direction are different, reset the image index and last direction update time.
        if (previousDirection != d) {
            lastImageIndex = 0;
            lastDirectionUpdate = System.currentTimeMillis();
        } else if(System.currentTimeMillis() - lastImageUpdate > getImageRefreshRate()){
            // If it's time to refresh the image, increment the image index.
            lastImageIndex++;
        } else {
            // Otherwise, don't update the image yet.
            return;
        }

        String[] icons;
        switch (currDirection) {
            case LEFT: icons = leftIcons; break;
            case RIGHT: icons = rightIcons; break;
            case UP: icons = backIcons; break;
            default: icons = frontIcons; break;
        }

        // Ensure the icon index is within bounds.
        if (lastImageIndex < 0 || lastImageIndex >= icons.length)
            lastImageIndex = 0;

        loadAndSetImage(icons[lastImageIndex]);
    }

    /**
     * Attempts to move the entity in the specified direction and updates the last direction if the move was successful.
     * @param d The direction to move the entity in.
     * @return true if the move was successful, false otherwise.
     */
    public boolean move(Direction d) {
        // If the entity is not alive, return true.
        if(!getAliveState()) return true;

        // Try to move or interact with the entity in the specified direction.
        if (moveOrInteract(d)){
            // If the move or interaction was successful, update the last direction and return true.
            updateLastDirection(d);
            return true;
        }

        // Otherwise, return false.
        return false;
    }
    /**

     Handles the move command by first attempting to move the player in the direction specified by the command.

     If the move is not successful, it attempts to overpass a block by calling the overpassBlock method.

     @param command The command specifying the direction of movement.

     @param oppositeDirection1 The opposite direction to the command, which is used for overpassing a block.

     @param oppositeDirection2 The second opposite direction to the command, which is used for overpassing a block.
     */
    public void handleMoveCommand(Command command, Direction oppositeDirection1, Direction oppositeDirection2) {
        boolean moveSuccessful = move(command.commandToDirection());

        if (moveSuccessful) {
            return;
        }

        List<Coordinates> oppositeBlocksCoordinates = getNewCoordinatesOnDirection(command.commandToDirection(), getSize(), getSize());
        List<Entity> entitiesOpposite1 = getEntitiesOnCoordinates(oppositeBlocksCoordinates.get(0));
        List<Entity> entitiesOpposite2 = getEntitiesOnCoordinates(oppositeBlocksCoordinates.get(1));
        overpassBlock(entitiesOpposite1,entitiesOpposite2,oppositeDirection1,oppositeDirection2);
    }

    /**
     Attempts to overpass a block in the opposite direction of the player's movement.
     The method checks for the existence of obstacles and the player input to determine the appropriate direction to move.

     @param entitiesOpposite1 A list of entities on the first opposite coordinate of the player's movement.
     @param entitiesOpposite2 A list of entities on the second opposite coordinate of the player's movement.
     @param direction1 The first opposite direction to the player's movement.
     @param direction2 The second opposite direction to the player's movement.
     */
    public void overpassBlock(List<Entity> entitiesOpposite1, List<Entity> entitiesOpposite2, Direction direction1, Direction direction2) {
        Command oppositeCommand1 = direction1.toCommand();
        Command oppositeCommand2 = direction2.toCommand();
        boolean doubleClick1 = false;
        boolean doubleClick2 = false;

        // Check if this is the player instance and check for input
        if (this == BomberMan.getInstance().getPlayer()) {
            ControllerManager controllerManager = BomberMan.getInstance().getControllerManager();
            doubleClick1 = controllerManager.isCommandPressed(oppositeCommand1);
            doubleClick2 = controllerManager.isCommandPressed(oppositeCommand2);
        }

        // If the first direction has no obstacles and the second does, and the second direction is not double-clicked, move in the second direction.
        if (!entitiesOpposite1.isEmpty() && entitiesOpposite2.isEmpty() && !doubleClick2) {
            move(direction2);
        }
        // If the second direction has no obstacles and the first does, and the first direction is not double-clicked, move in the first direction.
        else if (!entitiesOpposite2.isEmpty() && entitiesOpposite1.isEmpty() && !doubleClick1) {
            move(direction1);
        }
    }

    /**

     Handles the action by calling handleMoveCommand with the appropriate opposite directions depending on the command.
     @param command The command specifying the action.
     */
    public void handleAction(Command command) {
        if (!BomberMan.getInstance().getGameState()) {
            return;
        }

        switch (command) {
            // For move up and move down, use left and right as opposite directions respectively.
            case MOVE_UP: case MOVE_DOWN: handleMoveCommand(command, LEFT, RIGHT); break;
            // For move left and move right, use up and down as opposite directions respectively.
            case MOVE_LEFT: case MOVE_RIGHT: handleMoveCommand(command, UP, DOWN); break;
        }
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return super.getObstacles();
    }

    public float getSpeed() {
        return 1f;
    }

    @Override
    protected float getDelayObserverUpdate() {
        return super.getDelayObserverUpdate() / getSpeed();
    }
    public int getHp(){
        return healthPoints;
    }
    public void setHp(int newHp){
        healthPoints = newHp;
    }
    public void removeHp(int damage){
        healthPoints -=damage;
        if (healthPoints<=0){
            healthPoints = 0;
            despawn();
        }
    }

    public int getPaddingHead(){
        return PADDING_HEAD;
    }

}
