package game.entity;

import game.BomberMan;
import game.models.Coordinates;
import game.models.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;


public abstract class Entity {
    private Coordinates coords;
    private boolean isSpawned = false;
    private long id;


    private Entity(){}

    public Entity(Coordinates coordinates){
        id = UUID.randomUUID().getMostSignificantBits();
        setCoords(coordinates);
    }

    public abstract void interact(Entity e);
    public abstract int getSize();
    public abstract Image getImage();

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

    public void despawn() {
        setSpawned(false);
        BomberMan.getInstance().removeEntity(this);
    }

    public void spawn(){
        if (isSpawned()) {
            return;
        }

        List<Coordinates> desiredPosition = getPositions();
        boolean check = desiredPosition.stream().anyMatch(coordinates -> BomberMan.getInstance().getEntities().contains(coordinates));

        if (!check) {
            setCoords(coords);
            setSpawned(true);
            BomberMan.getInstance().addEntity(this);
        }
    }

    public void move(int x, int y) {
        setCoords(new Coordinates(x,y));
    }

    public List<Coordinates> getNewCoordinatesOnDirection(Direction d, int entitySize){
        return getNewCoordinatesOnDirection(d, entitySize, 1,1,false);
    }

    public List<Coordinates> getNewCoordinatesOnDirection(Direction d, int size, int steps, int offset, boolean topLeftCoords){
        List<Coordinates> desiredCoords = new ArrayList<>();

        int x = 0;
        int y = 0;

        if(topLeftCoords) {
            x = - size;
            y = - size;
        }

        switch (d) {
            case RIGHT: return getNewCoordinatesOnRight(steps, offset, size);
            case LEFT: return getNewCoordinatesOnLeft(steps, offset, x);
            case UP: return getNewCoordinatesOnUp(steps, offset, y);
            case DOWN: return getNewCoordinatesOnDown(steps, offset, size);
        }


        return desiredCoords;
    }

    private List<Coordinates> getNewCoordinatesOnRight(int steps, int offset, int size) {
        List<Coordinates> coordinates = new ArrayList<>();

        for (int step = 0; step < steps / offset; step++)
            for (int i = 0; i < getSize() / offset; i++)
                coordinates.add(new Coordinates(getCoords().getX() + size + step * offset, getCoords().getY() + i * offset));

        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnLeft(int steps, int offset, int x) {
        List<Coordinates> coordinates = new ArrayList<>();

        for (int step = 0; step < steps/offset; step++)
            for (int i = 0; i < getSize()/offset; i++)
                coordinates.add(new Coordinates(getCoords().getX() - 1 - step*offset + x, getCoords().getY() + i*offset));

        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnUp(int steps, int offset, int y) {
        List<Coordinates> coordinates = new ArrayList<>();

        for (int step = 0; step < steps/offset; step++)
            for (int i = 0; i < getSize()/offset; i++)
                coordinates.add(new Coordinates(getCoords().getX() + i * offset, getCoords().getY() + y - 1 - step * offset));

        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnDown(int steps, int offset, int size) {
        List<Coordinates> coordinates = new ArrayList<>();

        for (int step = 0; step < steps / offset; step++)
            for (int i = 0; i < getSize() / offset; i++)
                coordinates.add(new Coordinates(getCoords().getX() + i*offset, getCoords().getY() + size + step * offset));

        return coordinates;
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
        return Objects.hash(id, isSpawned, getCoords());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "coords=" + coords +
                ", isSpawned=" + isSpawned +
                ", id=" + id +
                '}';
    }
}
