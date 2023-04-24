package game.entity;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bomb extends Block{
    @Override
    public Image getImage() {
        try {
            return ImageIO.read(new File("assets/Bomb/bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Bomb(Coordinates coords){
        super(coords);
    }

    @Override
    public void interact(Entity e) {

    }
    public void explode(){
        List<Coordinates> north = getNewCoordinatesOnDirection(Direction.UP,getSize());
        List<Coordinates> east = getNewCoordinatesOnDirection(Direction.RIGHT,getSize());
        List<Coordinates> south = getNewCoordinatesOnDirection(Direction.DOWN,getSize());
        List<Coordinates> west = getNewCoordinatesOnDirection(Direction.LEFT,getSize());

        for (int i = 0; i < north.size(); i++){
            new Explosion(north.get(i), Direction.UP,this,1,4);
            new Explosion(east.get(i), Direction.RIGHT,this,1,4);
            new Explosion(south.get(i), Direction.DOWN,this,1,4);
            new Explosion(west.get(i), Direction.LEFT,this,1,4);
        }
    }

    public int getSize(){
        return Utility.px(50);
    }


}

