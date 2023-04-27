package game.entity;

import game.BomberMan;
import game.models.Coordinates;
import game.models.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static game.ui.GamePanel.PIXEL_UNIT;


/**
 * An abstract class representing interactive entities, which can move or interact with other entities in the game.
 */
public abstract class InteractiveEntities extends Entity {

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
    }

    /**
     * Gets a list of entities that are on the border of the current entity in the given direction, with the default step size and offset.
     * @param d the direction to get the entities on the border of
     * @return the list of entities on the border in the given direction
     */
    public List<Entity> getInteractiveEntitiesOnBorder(Direction d){
        return getInteractiveEntitiesOnBorder(d, PIXEL_UNIT, PIXEL_UNIT);
    }

    /**
     * Gets a list of entities that are on the border of the current entity in the given direction, with the given step size and offset.
     * @param d the direction to get the entities on the border of
     * @param steps the step size to use
     * @param offset the offset to use
     * @return the list of entities on the border in the given direction
     */


    public List<Entity> getInteractiveEntitiesOnBorder(Direction d, int steps, int offset){
        return getEntitiesOnBorder(d,steps,offset, BomberMan.getInstance().getEntities());
    }

    public List<Entity> getBlocksOnBorder(Direction d, int steps, int offset){
        return getEntitiesOnBorder(d,steps,offset, BomberMan.getInstance().getBlocks());
    }

    private List<Entity> getEntitiesOnBorder(Direction d, int steps, int offset, Set<? extends Entity> set ){
        List<Coordinates> desiredCoords = getNewCoordinatesOnDirection(d, steps, offset);
        List<Entity> outputEntities = new ArrayList<>();

        for (Entity entity : set) {
            for (Coordinates element : desiredCoords) {
                if (entity.getPositions().contains(element)) {
                    outputEntities.add(entity);
                }
            }
        }

        return outputEntities;
    }

    /**
     * Gets the next coordinates in the given direction and with the given step size.
     * @param d the direction to get the next coordinates in
     * @param stepSize the step size to use
     * @return the next coordinates in the given direction and with the given step size
     */
    public Coordinates nextCoords(Direction d, int stepSize){
        int x = 0;
        int y = 0;

        switch (d) {
            case RIGHT: x = stepSize; break;
            case LEFT: x = - stepSize; break;
            case UP: y = - stepSize; break;
            case DOWN: y = stepSize; break;
        }

        return new Coordinates(x + getCoords().getX(), y + getCoords().getY());
    }

    /**
     * Moves or interacts with other entities in the given direction and with the default step size and offset.
     * @param d the direction to move or interact in
     */
    public void moveOrInteract(Direction d) {
        moveOrInteract(d, PIXEL_UNIT);
    }

    /**
     * Moves or interacts with other entities in the given direction and with the given step size and default offset.
     * @param d the direction to move or interact in
     * @param stepSize the step size to use
     */
    public void moveOrInteract(Direction d, int stepSize){
        moveOrInteract(d,stepSize,getSize()/2);
    }

    public void moveOrInteract(Direction d, int stepSize,int offset) {

        Coordinates nextCoordinates = nextCoords(d, stepSize);

        List<Entity> outputEntities = getInteractiveEntitiesOnBorder(d, stepSize, offset);
        List<Entity> blockEntities = getBlocksOnBorder(d,stepSize,offset);

        if (!(blockEntities.isEmpty())) {
            blockEntities.forEach(this::interact);
        } else {
            setCoords(nextCoordinates);
            outputEntities.forEach(this::interact);
        }

    }
}
