package game.entity;

import game.BomberMan;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Explosion extends InteractiveEntities{
    private final Bomb bomb;
    private final int distanceFromBomb;
    private final int maxDistance;
    public final Direction direction;


    public Explosion(Coordinates coordinates, Direction direction, Bomb bomb, int distanceFromBomb, int maxDistance) {
        super(coordinates);
        this.direction = direction;
        this.bomb = bomb;
        this.distanceFromBomb = distanceFromBomb;
        this.maxDistance = maxDistance;
        BomberMan.getInstance().addEntity(this);
        moveOrInteract(direction);
    }

    @Override
    public void interact(Entity e) {
        if (!(e instanceof StoneBlock)){
            e.despawn();
        }
    }

    @Override
    public int getSize() {
        return Utility.px(10);
    }

    @Override
    public void move(int x, int y){
        if (distanceFromBomb <= maxDistance){
            super.move( x,y);
            new Explosion(new Coordinates(x,y), direction, bomb,distanceFromBomb + 1,maxDistance);
        }
    }


    @Override
    public Image getImage(){
        try {
            return ImageIO.read(new File("assets/Bomb/flame_up_left.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
