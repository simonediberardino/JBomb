package game.entity;

import game.BomberMan;
import game.models.Coordinates;
import game.models.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static game.entity.Character.STEP_SIZE;

public abstract class InteractiveEntities extends Entity{
    public InteractiveEntities(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void interact(Entity e) {

    }

    public List<Entity> getEntitiesOnBorder(Direction d){
        return getEntitiesOnBorder(d,1,1);
    }

    public List<Entity> getEntitiesOnBorder(Direction d, int steps, int offset){
        List<Coordinates> desiredCoords = getNewCoordinatesOnDirection(d,getSize(),steps,offset,false);
        List<Entity> outputEntities = new ArrayList<>();

        for (Entity entity : BomberMan.getInstance().getEntities()) {
            for (Coordinates element : desiredCoords) {
                if (entity.getPositions().contains(element)) {
                    outputEntities.add(entity);

                }
            }
        }
        return outputEntities;
    }



    public void moveOrInteract(Direction d) {
        int x = 0;
        int y = 0;

        switch (d) {
            case RIGHT: x = STEP_SIZE; break;
            case LEFT: x = - STEP_SIZE; break;
            case UP: y = - STEP_SIZE; break;
            case DOWN: y = STEP_SIZE; break;
        }

        List<Entity> outputEntities = getEntitiesOnBorder(d);
        if (outputEntities.isEmpty()) {
            move(x + getCoords().getX(), y + getCoords().getY());
        }

        if (Stream.of(outputEntities).noneMatch(e -> e instanceof Block))
            for (Entity e : outputEntities) interact(e);
    }
    public abstract int getSize();

}
