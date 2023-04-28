package game.entity.models;

import game.BomberMan;
import game.entity.Bomb;
import game.entity.Explosion;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static game.ui.GamePanel.PIXEL_UNIT;


/**
 * An abstract class representing interactive entities, which can move or interact with other entities in the game.
 */
public abstract class InteractiveEntities extends Entity {
    private boolean bombImmune = false;

    /**
     * Gets the size of the entity in pixels.
     * @return the size of the entity
     */
    public abstract int getSize();

    /**
     * Constructs an interactive entity with the given coordinates.
     * @param coordinates the coordinates of the entity
     */
    public InteractiveEntities(Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Interacts with the given entity.
     * @param e the entity to interact with
     */
    @Override
    public void interact(Entity e) {
        // This method can be overridden by subclasses to implement specific interaction logic.
    }

    /**
     * Gets a list of available directions for the entity to move, based on the current game state.
     * A direction is considered available if moving in that direction would not result in a collision
     * with another entity or an invalid location.
     *
     * @return a list of available directions
     */
    public List<Direction> getAvailableDirections() {
        List<Direction> result = new LinkedList<>();

        // Iterate over each direction
        for (Direction d : Direction.values()) {
            List<Coordinates> newCoordinates = getNewCoordinatesOnDirection(d, getSize(), getSize() / 2);
            // Check if any entities on the next coordinates are blocks or have invalid coordinates
            boolean areCoordinatesValid = getEntitiesOnCoordinates(
                    newCoordinates
            ).parallelStream().noneMatch(e -> e instanceof Block);

            areCoordinatesValid = areCoordinatesValid && newCoordinates.stream().allMatch(Coordinates::validate);
            // If all the next coordinates are valid, add this direction to the list of available directions
            if (areCoordinatesValid) {
                result.add(d);
            }
        }

        return result;
    }

    /**
     * Gets a list of entities that occupy the specified coordinates.
     * @param desiredCoords the coordinates to check for occupied entities
     * @return a list of entities that occupy the specified coordinates
     */
    public List<Entity> getEntitiesOnCoordinates(List<Coordinates> desiredCoords){
        List<Entity> entityLinkedList = new LinkedList<>();

        // Get all the blocks and entities in the game
        var blocks = BomberMan.getInstance().getBlocks();
        var entities = BomberMan.getInstance().getEntities();
        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        // Check for each entity if it occupies the specified coordinates
        for(Entity e : all) {
            for (Coordinates coord : desiredCoords) {
                int entityBottomRightX = e.getCoords().getX() + e.getSize() - 1;
                int entityBottomRightY = e.getCoords().getY() + e.getSize() - 1;

                if (coord.getX() >= e.getCoords().getX()
                        && coord.getX() <= entityBottomRightX
                        && coord.getY() >= e.getCoords().getY()
                        && coord.getY() <= entityBottomRightY
                ) {
                    entityLinkedList.add(e);
                }
            }
        }

        return entityLinkedList;
    }

    /**
     * Gets a list of entities that occupy the specified coordinate.
     * @param coord the coordinate to check for occupied entities
     * @return a list of entities that occupy the specified coordinate
     */
    public List<Entity> getEntitiesOnCoordinates(Coordinates coord) {
        // Get all the blocks and entities in the game
        var blocks = BomberMan.getInstance().getBlocks();
        var entities = BomberMan.getInstance().getEntities();
        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        // Use Java stream to filter entities that collide with the specified coordinate
        return all.parallelStream().filter(e -> doesCollideWith(coord, e)).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Checks if the given coordinates collide with the given entity.
     *
     * @param coord the coordinates to check for collision
     * @param e     the entity to check for collision with
     * @return true if the given coordinates collide with the given entity, false otherwise
     */
    public boolean doesCollideWith(Coordinates coord, Entity e) {
        // Get the coordinates of the bottom-right corner of the entity
        int entityBottomRightX = e.getCoords().getX() + e.getSize() - 1;
        int entityBottomRightY = e.getCoords().getY() + e.getSize() - 1;

        // Check if the given coordinates collide with the entity
        return (coord.getX() >= e.getCoords().getX()
                && coord.getX() <= entityBottomRightX
                && coord.getY() >= e.getCoords().getY()
                && coord.getY() <= entityBottomRightY);
    }

    /**
     Gets the next coordinates in the given direction and with the given step size.
     @param d the direction to get the next coordinates in
     @param stepSize the step size to use
     @return the next coordinates in the given direction and with the given step size
     */
    public Coordinates nextCoords(Direction d, int stepSize) {
        int x = 0;
        int y = 0;

        // Determine the direction to move in
        switch (d) {
            case RIGHT: x = stepSize;break;
            case LEFT: x = -stepSize;break;
            case UP: y = -stepSize; break;
            case DOWN: y = stepSize; break;
        }

        // Calculate the next coordinates based on the current direction and step size
        return new Coordinates(x + getCoords().getX(), y + getCoords().getY());
    }

    /**

     Moves or interacts with other entities in the given direction and with the default step size and offset.
     @param d the direction to move or interact in
     @return true if the entity can move in the given direction, false otherwise
     */
    public boolean moveOrInteract(Direction d) {
        // Call the moveOrInteract method with the default step size
        return moveOrInteract(d, PIXEL_UNIT);
    }


    /**
     * Moves or interacts with other entities in the given direction and with the given step size and default offset.
     * @param d the direction to move or interact in
     * @param stepSize the step size to use
     */
    public boolean moveOrInteract(Direction d, int stepSize) {
        // Get the coordinates of the next positions that will be occupied if the entity moves in a certain direction
        // with a given step size
        List<Coordinates> nextOccupiedCoords = getNewCoordinatesOnDirection(d, stepSize, getSize() / 2);
        // Calculate the coordinates of the top-left corner of the next position
        Coordinates nextTopLeftCoords = nextCoords(d, stepSize);
        // Get a list of entities that are present in the next occupied coordinates
        List<Entity> interactedEntities = getEntitiesOnCoordinates(nextOccupiedCoords);

        if(!nextTopLeftCoords.validate()){
            this.interact(null);
            return false;
        }

        // If there are no entities present in the next occupied coordinates, update the entity's position
        if (interactedEntities.isEmpty()) {
            setCoords(nextTopLeftCoords);
            return true;
        }

        // Initialize a flag to indicate whether the entity has interacted with a bomb
        AtomicBoolean didInteractWithBomb = new AtomicBoolean(false);

        // Initialize a flag to indicate whether the entity can move
        boolean canMove = true;

        // Loop through the list of entities present in the next occupied coordinates
        for (Entity e : interactedEntities) {
            // If the entity interacts with a bomb, it cannot move further
            if (e instanceof Bomb) {
                canMove = false;
                didInteractWithBomb.set(true);

                // If the entity is inside the bomb, it can move through the bomb block: this is to prevent the bomb from
                // blocking the entity that spawned it;
                if (this.isInside(e)) {
                    bombImmune = true;
                }
            }
            // If the entity interacts with a block, it cannot move further
            else if (e instanceof Block) {
                canMove = false;
                break;
            }

            // Call the 'interact' method of the entity the entity is interacting with
            this.interact(e);
        }

        // If the entity can move or it is immune to bombs, update the entity's position
        if (canMove || bombImmune) {
            setCoords(nextTopLeftCoords);
        }
        // If the entity is an instance of 'Explosion' and it cannot move further, stop expanding the explosion
        else if (this instanceof Explosion) {
            ((Explosion) this).cantExpandAnymore();
        }

        // If the entity did not interact with any bomb, it is not immune to bombs
        if (!didInteractWithBomb.get()) {
            bombImmune = false;
        }

        // Return whether the entity can move or not
        return canMove;
    }
}
