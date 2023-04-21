package game.entity;

import game.BomberMan;
import game.ui.UIHandler;

import javax.swing.*;
import java.util.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public abstract class Entity {
    private long id;
    private boolean isSpawned = false;
    private Coordinates coords;

    abstract Icon[] getIcon();

    private Entity(){}

    public Entity(Coordinates coordinates){
        id = UUID.randomUUID().getMostSignificantBits();
        setCoords(coordinates);
    }

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
        int endX = getCoords().getX() + BLOCK_SIZE - 1;

        int startY = getCoords().getY();
        int endY = getCoords().getY() + BLOCK_SIZE - 1;

        for (int x = startX; x <= endX; x++)
            for (int y = startY; y <= endY; y++)
                result.add(new Coordinates(x, y));

        return result;
    }

    public void spawn(){
        if (isSpawned()) {
            return;
        }

        setPosition(false, getCoords().getX(), getCoords().getY());
        BomberMan.getInstance().addEntity(this);
        setSpawned(true);
    }

    public void setPosition(int x, int y){
        setPosition(true, x, y);
    }

    public void setPosition(boolean delete, int x, int y){
        Icon[] generateImageArray = getIcon();
        if(delete) delete();
        setCoords(new Coordinates(x, y));

        int z = 0;
        for(int y1 = 0; y1 < BLOCK_SIZE; y1++){
            for(int x1 = 0; x1 < BLOCK_SIZE; x1++){
                JButton b = UIHandler.positions[y1+y][x1+x];
                UIHandler.setIcon(b, generateImageArray[z]);
                z++;
            }
        }
    }


    public void delete(){
        List<Coordinates> coordinates = getPositions();
        Icon[] grassBlocks = BomberMan.getInstance().getCurrentLevel().getGrassBlock();

        for (Coordinates c : coordinates) {
            int x = c.getX() % BLOCK_SIZE;
            int y = c.getY() % BLOCK_SIZE;

            JButton b = UIHandler.positions[c.getY()][c.getX()];
            UIHandler.setIcon(b, grassBlocks[(y) * BLOCK_SIZE + x]);
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
        return Objects.hash(id, isSpawned, getCoords());
    }
}
