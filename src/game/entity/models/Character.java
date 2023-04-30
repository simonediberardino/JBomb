package game.entity.models;

import game.BomberMan;
import game.controller.Command;
import game.controller.ControllerManager;
import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.GamePanel;

import java.awt.image.BufferedImage;
import java.util.List;

import static game.models.Direction.*;
import static game.models.Direction.DOWN;


/**
 * Represents a character in the game, which can move and interact with the environment.
 */
public abstract class Character extends InteractiveEntities {
    protected long lastDirectionUpdate = 0;
    protected Direction currDirection = null;
    /** The last direction this character was moving in. */
    protected Direction previousDirection = null;

    /** Whether this character is alive or not. */
    protected boolean isAlive = true;
    public static final int PADDING_HEAD = GamePanel.PIXEL_UNIT*4;
    public static final int SIZE = GamePanel.PIXEL_UNIT * 4 * 2;

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
            lastDirectionUpdate = System.currentTimeMillis();
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

    public boolean move(Direction d) {
        if(!getAliveState()) return true;

        if (moveOrInteract(d)){
            updateLastDirection(d);
            return true;
        }

        return false;
    }

    @Override
    public void interact(Entity e) {
        super.interact(e);

        if(e instanceof Explosion){
            despawn();
        }
    }

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

    public void overpassBlock(List<Entity> entitiesOpposite1, List<Entity> entitiesOpposite2, Direction direction1, Direction direction2) {
        Command oppositeCommand1 = direction1.toCommand();
        Command oppositeCommand2 = direction2.toCommand();
        boolean doubleClick1 = false;
        boolean doubleClick2 = false;

        if (this == BomberMan.getInstance().getPlayer()) {
            ControllerManager controllerManager = BomberMan.getInstance().getControllerManager();
            doubleClick1 =controllerManager.isCommandPressed(oppositeCommand1);
            doubleClick2 =controllerManager.isCommandPressed(oppositeCommand2);
        }

        if (!entitiesOpposite1.isEmpty() && entitiesOpposite2.isEmpty() && !doubleClick2) {
            move(direction2);
        } else if (!entitiesOpposite2.isEmpty() && entitiesOpposite1.isEmpty() && !doubleClick1) {
            move(direction1);
        }
    }

    public void handleAction(Command command) {
        switch (command) {
            case MOVE_UP: case MOVE_DOWN: handleMoveCommand(command, LEFT, RIGHT); break;
            case MOVE_LEFT: case MOVE_RIGHT: handleMoveCommand(command, UP, DOWN); break;
        }
    }
}
