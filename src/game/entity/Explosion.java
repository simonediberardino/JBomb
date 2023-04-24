package game.entity;

import game.BomberMan;
import game.models.Direction;
import game.ui.GridImage;

import javax.swing.*;

import static game.ui.UIHandler.GRID_SIZE;

public class Explosion extends InteractiveEntities{
    private final Bomb bomb;
    private final int distanceFromBomb;
    private final int maxDistance;
    public final Direction direction;


    public Explosion(Coordinates coordinates,Direction direction, Bomb bomb, int distanceFromBomb, int maxDistance) {
        super(coordinates);
        this.direction = direction;
        this.bomb = bomb;
        this.distanceFromBomb = distanceFromBomb;
        this.maxDistance = maxDistance;
        int[] nextCoords = getDirectionCoords(direction,getCoords().getX(),getCoords().getY());
        BomberMan.getInstance().addEntity(this);
        moveOrInteract(direction,false);
    }


    @Override
    public void interact(Entity e) {
        if (!(e instanceof StoneBlock)){
            e.despawn();
        }
    }

    @Override
    public int getSize() {
        return 1;
    }

    public Bomb getBomb(){
        return bomb;
    }

    public int getDistanceFromBomb() {
        return distanceFromBomb;
    }
    public int getMaxDistance() {
        return maxDistance;
    }

    @Override
    public void move(int x,int y){
        move(true,x,y);
    }
    @Override
    public void move(boolean delete, int x, int y){
        if (distanceFromBomb<=maxDistance){
            super.move(false, x,y);
            Explosion newExplosion = new Explosion(new Coordinates(x,y),direction,bomb,distanceFromBomb+1,maxDistance);

        }

    }

    public Icon[] getIcon(){
        return new GridImage("assets/Bomb/flame_up_left.png", getSize()).generate();
    }




}
