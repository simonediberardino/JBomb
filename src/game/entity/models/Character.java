package game.entity.models;

import game.BomberManMatch;
import game.Bomberman;
import game.controller.Command;
import game.controller.ControllerManager;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.panels.game.PitchPanel;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static game.models.Direction.*;
import static game.models.Direction.DOWN;


/**
 * Represents a character in the game, which can move and interact with the environment.
 */
public abstract class Character extends MovingEntity {
    public static final int SIZE = PitchPanel.PIXEL_UNIT * 4 * 2;
    protected long lastDirectionUpdate = 0;
    protected Direction currDirection = Direction.values()[(int) (Math.random() * values().length)];
    /** The last direction this character was moving in. */
    protected Direction previousDirection = null;
    /** Whether this character is alive or not. */
    protected boolean isAlive = true;
    protected boolean isImmune = false;
    private int maxHp = 100;
    private int healthPoints = maxHp;
    protected boolean canMove = true;

    /**
     * Returns an array of file names for the front-facing icons for this character.
     *
     * @return an array of file names for the front-facing icons
     */
    public abstract String[] getBaseSkins();

    /**
     * Returns an array of file names for the left-facing icons for this character.
     *
     * @return an array of file names for the left-facing icons
     */
    public String[] getLeftIcons() {
        return getBaseSkins();
    }

    /**
     * Returns an array of file names for the back-facing icons for this character.
     *
     * @return an array of file names for the back-facing icons
     */
    public String[] getBackIcons() {
        return getBaseSkins();
    }

    /**
     * Returns an array of file names for the right-facing icons for this character.
     *
     * @return an array of file names for the right-facing icons
     */
    public String[] getRightIcons() {
        return getBaseSkins();
    }

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
            return loadAndSetImage(getBaseSkins()[0]);
        }
    }

    public boolean isImmune() {
        return isImmune;
    }

    public void setImmune(boolean immune) {
        isImmune = immune;
    }

    protected boolean useOnlyBaseIcons() {
        return false;
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        isAlive = false;
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        isAlive = true;
    }

    /**
     * Updates the last direction the entity moved in and sets the appropriate image based on the direction.
     * @param d The new direction the entity is moving in.
     */
    protected void updateLastDirection(Direction d) {
        String[] baseIcons = getBaseSkins();

        // If the character doesn't have custom images for each direction, do not check if the direction has changed;
        if(useOnlyBaseIcons()){
            if(System.currentTimeMillis() - lastImageUpdate > getImageRefreshRate()){
                // If it's time to refresh the image, increment the image index.
                lastImageIndex++;
            }else return;

            // Ensure the icon index is within bounds.
            if (lastImageIndex < 0 || lastImageIndex >= baseIcons.length)
                lastImageIndex = 0;

            loadAndSetImage(baseIcons[lastImageIndex]);

            return;
        }

        String[] leftIcons = getLeftIcons();
        String[] backIcons = getBackIcons();
        String[] rightIcons = getRightIcons();

        // If both previousDirection and currDirection are null, initialize currDirection to d and load the front-facing image.
        if(previousDirection == null && currDirection == null){
            currDirection = d;
            loadAndSetImage(baseIcons[0]);
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
            default: icons = baseIcons; break;
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
        if (this == Bomberman.getMatch().getPlayer()) {
            ControllerManager controllerManager = Bomberman.getMatch().getControllerManager();
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
        if (!Bomberman.getMatch().getGameState() || !canMove) {
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
    protected float getDelayObserverUpdate(){
        return super.getDelayObserverUpdate() / getSpeed();
    }

    /**
     * Starts a damage animation that makes the entity invisible and visible
     * repeatedly for a certain number of iterations with a delay between each iteration.
     * The animation lasts for a duration of {@code EntityInteractable.INTERACTION_DELAY_MS} milliseconds.
     */
    protected void startDamageAnimation() {
        // Duration of each iteration of the animation
        int durationMs = 100;
        // Calculate the number of iterations required to reach the total duration
        int iterations = (int) (EntityInteractable.INTERACTION_DELAY_MS / durationMs);

        // Create a Timer object to schedule the animation iterations
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            // Counter to keep track of the number of iterations
            int count = 0;

            @Override
            public void run() {
                // If the number of iterations has been reached, cancel the timer and return
                if (count >= iterations || !isAlive) {
                    timer.cancel();
                    return;
                }

                try {
                    // Make the entity invisible and wait for the specified duration
                    setInvisible(true);
                    Thread.sleep(durationMs);
                    // Make the entity visible again and wait for the specified duration
                    setInvisible(false);
                    Thread.sleep(durationMs);
                } catch (InterruptedException ignored) { }

                // Increment the counter to keep track of the number of iterations
                count++;
            }
        }, 0, durationMs * 2); // Schedule the timer to repeat with a fixed delay of durationMs * 2 between iterations
    }

    protected int getMaxHp() {
        return maxHp;
    }

    protected void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * Returns the current health points of the entity.
     * @return The current health points of the entity.
     */
    protected int getHp(){
        return healthPoints;
    }

    /**
     * Sets the health points of the entity to the specified value.
     * @param newHp The new health points to set for the entity.
     */
    protected void setHp(int newHp){
        healthPoints = newHp;
    }

    protected int getHpPercentage() {
        return (int) (((float) getHp() / (float) getMaxHp()) * 100);
    }

    @Override
    public int getDrawPriority() {
        return 2;
    }

    /**
     * Removes the specified amount of damage from the entity's health points.
     * If the health points reach 0 or below, the entity is despawned.
     * Otherwise, a damage animation is started.
     * @param damage The amount of damage to remove from the entity's health points.
     */
    protected final synchronized void attackReceived(int damage) {
        if(System.currentTimeMillis() - lastDamageTime < INTERACTION_DELAY_MS)
            return;

        lastDamageTime = System.currentTimeMillis();
        // Reduce the health points by the specified amount
        healthPoints -= damage;

        // If the health points reach 0 or below, despawn the entity
        if (healthPoints <= 0){
            healthPoints = 0;
            die();
        } else {
            // Otherwise, start a damage animation
            startDamageAnimation();
            onHit(damage);
        }
    }

    protected void die() {
        onDie();
    }

    protected synchronized void onDie(){
        canMove = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            // Counter to keep track of the number of iterations
            int count = 0;
            //TODO animation count, temporarily ==2
            int animationCount = 2;
            @Override
            public void run() {
                if (count >= animationCount) {
                    timer.cancel();
                    despawn();
                    return;
                }
                //TODO
                loadAndSetImage("assets/entities/player/player_front_"+ count+ ".png");
                count++;
            }
        }, 0, EntityInteractable.INTERACTION_DELAY_MS); // Schedule the timer to repeat with a fixed delay of durationMs * 2 between iterations
    }



    protected void onHit(int damage){}
}
