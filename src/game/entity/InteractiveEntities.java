package game.entity;

import game.BomberMan;
import game.models.Direction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class InteractiveEntities extends Entity{
    public InteractiveEntities(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public abstract Icon[] getIcon();

    @Override
    public void interact(Entity e) {

    }



    public void moveOrInteract(Direction d){
        moveOrInteract(d,true);
    }


    public List<Entity> getEntitiesOnBorder(Direction d){
        List<Coordinates> desiredCoords = getCoordsOnBorder(d,getSize());
        List<Entity> outputEntities = new ArrayList<>();
        int x = 0;
        int y = 0;

        for (Entity entity : BomberMan.getInstance().getEntities()) {
            for (Coordinates element : desiredCoords) {
                if (entity.getPositions().contains(element)) {
                    if (entity instanceof Block) {
                        return null;
                    } else {
                        outputEntities.add(entity);
                    }
                }
            }
        }
        return outputEntities;
    }



    public void moveOrInteract(Direction d,boolean delete) {
        int x =0;
        int y = 0;
        if (d!=null) {
            switch (d) {
                case RIGHT:
                    x = 1;

                    break;
                case LEFT:
                    x = -1;

                    break;
                case UP:
                    y = -1;

                    break;
                case DOWN:
                    y = 1;

                    break;
            }
        }
        List<Entity> outputEntities = getEntitiesOnBorder(d);
        if (outputEntities.isEmpty()) {
            move(delete,x + getCoords().getX(), y + getCoords().getY());
        } else {
            for (Entity e : outputEntities) {
                interact(e);
            }
        }
    }
    public abstract int getSize();

}
