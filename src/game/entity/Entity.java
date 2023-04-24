package game.entity;

import game.BomberMan;
import game.models.Direction;
import game.ui.UIHandler;

import javax.swing.*;
import java.util.*;

import static game.ui.UIHandler.GRID_SIZE;


public abstract class Entity {
    private Coordinates coords;
    private boolean isSpawned = false;
    private long id;

    abstract Icon[] getIcon();

    private Entity(){}

    public Entity(Coordinates coordinates){
        id = UUID.randomUUID().getMostSignificantBits();
        setCoords(coordinates);
    }

    public abstract int getSize();

    public void setCoords(Coordinates coordinates){
        this.coords = coordinates;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public boolean isSpawned(){
        return isSpawned;
    }

    protected void setSpawned(boolean s){
        isSpawned = s;
    }

    public List<Coordinates> getPositions(){
        List<Coordinates> result = new ArrayList<>();

        int startX = getCoords().getX();
        int endX = getCoords().getX() + getSize() - 1;

        int startY = getCoords().getY();
        int endY = getCoords().getY() + getSize() - 1;

        for (int x = startX; x <= endX; x++)
            for (int y = startY; y <= endY; y++)
                result.add(new Coordinates(x, y));

        return result;
    }
    public void despawn(){
        setSpawned(false);
        delete();
        BomberMan.getInstance().getEntities().remove(this);
    }

    public void spawn(){
        /*
        if (isSpawned()) {
            return;
        }
        List<Coordinates> desiredPosition = getPositions();
        boolean check = desiredPosition.stream().anyMatch(coordinates -> BomberMan.getInstance().getEntities().contains(coordinates));

        if (!check) {
            move(false, getCoords().getX(), getCoords().getY());
            BomberMan.getInstance().addEntity(this);
            setSpawned(true);
        }*/
    }

    public void move(int x, int y){
        move(true, x, y);
    }

    public void move(boolean delete, int x, int y){
        Icon[] generateImageArray = getIcon();
        if(delete) delete();
        setCoords(new Coordinates(x, y));

        int z = 0;

        for(int y1 = 0; y1 < getSize(); y1++)
            for (int x1 = 0; x1 < getSize(); x1++) {
                JButton b = UIHandler.positions[y1 + y][x1 + x];
                UIHandler.setIcon(b, generateImageArray[z]);
                z++;
            }
    }


    public abstract void interact(Entity e);


    public List<Coordinates> getCoordsOnBorder(Direction d, int size){
        return getCoordsOnBorder(d, size, 1);
    }
    public List<Coordinates> getCoordsOnBorder(Direction d, int size, int steps){
        List<Coordinates> desiredCoords = new ArrayList<>();
        int x = 0;
        int y = 0;
        switch (d) {
            case RIGHT:
                x = 1;
                for (int step = 0; step < steps; step++) {
                    for (int i = 0; i < getSize(); i++) {
                        desiredCoords.add(new Coordinates(getCoords().getX() + size + step, getCoords().getY() + i));
                    }
                }
                break;
            case LEFT:
                x = -1;
                for (int step = 0; step < steps; step++) {
                    for (int i = 0; i < getSize(); i++) {
                        desiredCoords.add(new Coordinates(getCoords().getX() - 1 - step, getCoords().getY() + i));
                    }
                }
                break;
            case UP:
                y = -1;
                for (int step = 0; step < steps; step++) {
                    for (int i = 0; i < getSize(); i++) {
                        desiredCoords.add(new Coordinates(getCoords().getX() + i, getCoords().getY() - 1 - step));
                    }
                }
                break;
            case DOWN:
                y = 1;
                for (int step = 0; step < steps; step++) {
                    for (int i = 0; i < getSize(); i++) {
                        desiredCoords.add(new Coordinates(getCoords().getX() + i, getCoords().getY() + size + step));
                    }
                }
                break;
        }

        return desiredCoords;

    }


    public void delete(){
        List<Coordinates> coordinates = getPositions();
        Icon[] grassBlocks = BomberMan.getInstance().getCurrentLevel().getGrassBlock();

        for (Coordinates c : coordinates) {
            int x = c.getX() % GRID_SIZE;
            int y = c.getY() % GRID_SIZE;

            JButton b = UIHandler.positions[c.getY()][c.getX()];
            UIHandler.setIcon(b, grassBlocks[(y) * GRID_SIZE + x]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return id == entity.id && isSpawned == entity.isSpawned && getCoords().equals(entity.getCoords());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isSpawned, getCoords());}



    public static int[] getDirectionCoords(Direction d, int x, int y){
        switch (d){
            case DOWN: y +=1;break;
            case UP: y-=1;break;
            case LEFT: x-=1; break;
            case RIGHT: x+=1;break;
        }
        return new int[]{x,y};
    }
}
