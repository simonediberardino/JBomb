package game.entity.models;

import game.BomberMan;
import game.entity.Player;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.models.Direction;
import game.powerups.PowerUp;

import java.util.*;
import java.util.stream.Collectors;

import static game.panels.PitchPanel.GRID_SIZE;
import static game.panels.PitchPanel.PIXEL_UNIT;


/**
 * An abstract class representing interactive entities, which can move or interact with other entities in the game.
 */
public abstract class EntityInteractable extends Entity {
    private final static long INTERACTION_DELAY_MS = 100;
    private final Set<Class<? extends Entity>> whitelistObstacles = new HashSet<>();
    private final HashMap<Entity, Long> interactionMap = new HashMap<>();
    private int attackDamage = 100;

    /**
     * Gets the size of the entity in pixels.
     * @return the size of the entity
     */
    public abstract int getSize();

    /**
     * Constructs an interactive entity with the given coordinates.
     * @param coordinates the coordinates of the entity
     */
    public EntityInteractable(Coordinates coordinates) {
        super(coordinates);
    }

    public final void interact(Entity e) {
        if(e instanceof PowerUp) System.out.println("POWERUP");

        if(canInteractWith(e)) {
            this.doInteract(e);
            this.updateLastInteract(e);

        }

        else if(e instanceof EntityInteractable && ((EntityInteractable) e).canInteractWith(this)){
            e.doInteract(this);
            ((EntityInteractable )e).updateLastInteract(e);

        }
    }

    public void updateLastInteract(Entity e) {
        interactionMap.put(e, System.currentTimeMillis());
    }

    public long getLastInteraction(Entity e){
        return interactionMap.getOrDefault(e, 0L);
    }

    /**
     * Interacts with the given entity.
     * @param e the entity to interact with
     */
    @Override
    protected abstract void doInteract(Entity e);

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
            List<Coordinates> newCoordinates = getNewCoordinatesOnDirection(d, PIXEL_UNIT, getSize() / 2);
            // Check if any entities on the next coordinates are blocks or have invalid coordinates
            boolean areCoordinatesValid = getEntitiesOnCoordinates(
                    newCoordinates
            ).stream().noneMatch(this::isObstacle);

            areCoordinatesValid = areCoordinatesValid && newCoordinates.get(0).validate(this); // since the pitch is squared, if a coordinate is valid, then all the other new ones are as well;

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
        var blocks = BomberMan.getInstance().getStaticEntities();
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
     * @param nextOccupiedCoords the coordinate to check for occupied entities
     * @return a list of entities that occupy the specified coordinate
     */
    public List<Entity> getEntitiesOnCoordinates(Coordinates nextOccupiedCoords) {
        // Get all the blocks and entities in the game
        var blocks = BomberMan.getInstance().getStaticEntities();
        var entities = BomberMan.getInstance().getEntities();
        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        // Use Java stream to filter entities that collide with the specified coordinate
        return all.parallelStream().filter(e -> doesCollideWith(nextOccupiedCoords, e)).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Checks if the given coordinates collide with the given entity.
     *
     * @param nextOccupiedCoords the coordinates to check for collision
     * @param e     the entity to check for collision with
     * @return true if the given coordinates collide with the given entity, false otherwise
     */
    public static boolean doesCollideWith(Coordinates nextOccupiedCoords, Entity e) {
        return doesCollideWith(nextOccupiedCoords,e.getCoords(), e.getSize());
    }

    public static boolean doesCollideWith(Coordinates nextOccupiedCoords, Coordinates entityCoords) {
        return doesCollideWith(nextOccupiedCoords, entityCoords, GRID_SIZE);
    }

    private static boolean doesCollideWith(Coordinates nextOccupiedCoords, Coordinates entityCoords,int size) {
        // Get the coordinates of the bottom-right corner of the entity
        int entityBottomRightX = entityCoords.getX() + size - 1;
        int entityBottomRightY = entityCoords.getY() + size - 1;

        // Check if the given coordinates collide with the entity
        return (nextOccupiedCoords.getX() >= entityCoords.getX()
                && nextOccupiedCoords.getX() <= entityBottomRightX
                && nextOccupiedCoords.getY() >= entityCoords.getY()
                && nextOccupiedCoords.getY() <= entityBottomRightY);
    }

    public static boolean isBlockOccupied(Coordinates nextOccupiedCoords){
        // Get all the blocks and entities in the game
        var blocks = BomberMan.getInstance().getStaticEntities();
        var entities = BomberMan.getInstance().getEntities();
        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        // Use Java stream to filter entities that collide with the specified coordinate
        return all.parallelStream()
                .anyMatch(e -> doesCollideWith(Coordinates.roundCoordinates(nextOccupiedCoords), Coordinates.roundCoordinates(e.getCoords())));
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


    public boolean moveOrInteract(Direction d, int stepSize){
        return moveOrInteract(d, stepSize, false);
    }

    /**
     * Moves or interacts with other entities in the given direction and with the given step size and default offset.
     * @param d the direction to move or interact in
     * @param stepSize the step size to use
     */
    protected boolean moveOrInteract(Direction d, int stepSize, boolean ignoreMapBorders) {
        if(d == null) return false;

        // Get the coordinates of the next positions that will be occupied if the entity moves in a certain direction
        // with a given step size
        List<Coordinates> nextOccupiedCoords = getNewCoordinatesOnDirection(d, stepSize, GRID_SIZE/3 / 2);

        // Calculate the coordinates of the top-left corner of the next position
        Coordinates nextTopLeftCoords = nextCoords(d, stepSize);
        // Get a list of entities that are present in the next occupied coordinates
        List<Entity> interactedEntities = getEntitiesOnCoordinates(nextOccupiedCoords);

        if(!ignoreMapBorders && !nextTopLeftCoords.validate(this)){
            this.interact(null);
            return false;
        }

        //interactedEntities = interactedEntities.stream().filter(e-> canInteractWith(e) || isObstacle(e)).collect(Collectors.toList());

        // If there are no entities present in the next occupied coordinates, update the entity's position
        if (interactedEntities.isEmpty()) {
            setCoords(nextTopLeftCoords);
            return true;
        }

        // Initialize a flag to indicate whether the entity can move
        boolean canMove = true;

        // Loop through the list of entities present in the next occupied coordinates
        if (interactedEntities.stream().anyMatch(this::isObstacle)){
            List<Entity> temp = interactedEntities.stream().filter(this::isObstacle).collect(Collectors.toList());
            for (Entity e: temp) {
                interact(e);
            }
            canMove = false;
        }else{
            for (Entity e: interactedEntities) {
                interact(e);
            }
        }



        // If the entity can move or it is immune to bombs, update the entity's position
        if (canMove) {
            setCoords(nextTopLeftCoords);
        }
        // If the entity is an instance of 'Explosion' and it cannot move further, stop expanding the explosion
        else if (this instanceof Explosion) {
            ((Explosion) this).cantExpandAnymore();
        }


        // Return whether the entity can move or not
        return canMove;
    }

    public Set<Class<? extends Entity>> getWhiteListObstacles() {
        return whitelistObstacles;
    }

    public Set<Class<? extends Entity>> getObstacles() {
        return new HashSet<>(Arrays.asList(StoneBlock.class, Bomb.class, Enemy.class, DestroyableBlock.class, BomberEntity.class));
    }

    public abstract Set<Class<? extends Entity>> getInteractionsEntities();

    public boolean isObstacle(Entity e){
        return e == null || getObstacles().stream().anyMatch(c -> c.isInstance(e)) && whitelistObstacles.stream().noneMatch(c -> c.isInstance(e));
    }

    public boolean canInteractWith(Entity e){
        if(e == null) return true;
        if(System.currentTimeMillis() - getLastInteraction(e) < INTERACTION_DELAY_MS)
            return false;

        return getInteractionsEntities().stream().anyMatch(c -> c.isInstance(e)) && !e.isImmune() || this instanceof Enemy && isObstacle(e);
    }

    public int getAttackDamage(){
        return attackDamage;
    }

    public void setAttackDamage(int damage){
        attackDamage = damage;
    }

    public synchronized void attack(Entity e){
        if (e instanceof Character){
            ((Character) e).removeHp(getAttackDamage());
        }
        else if (e instanceof Block) ((Block) e).destroy();
    }
}
