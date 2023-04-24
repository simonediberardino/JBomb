package game.entity;

import game.BomberMan;
import game.models.Direction;
import game.ui.GridImage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static game.ui.UIHandler.GRID_SIZE;

public class Bomb extends Block{
    Icon[] getIcon() {
    return new GridImage("assets/Bomb/bomb.png", getSize()).generate();
    }



    public Bomb(Coordinates coords){
        super(coords);
    }

    @Override
    public void interact(Entity e) {

    }
    public void explode(){
        List<Coordinates> north = getCoordsOnBorder(Direction.UP,getSize());
        List<Coordinates> east = getCoordsOnBorder(Direction.RIGHT,getSize());
        List<Coordinates> south = getCoordsOnBorder(Direction.DOWN,getSize());
        List<Coordinates> west = getCoordsOnBorder(Direction.LEFT,getSize());

        for (int i= 0;i< north.size();i++){
            new Explosion(north.get(i), Direction.UP,this,1,4);
            new Explosion(east.get(i), Direction.RIGHT,this,1,4);
            new Explosion(south.get(i), Direction.DOWN,this,1,4);
            new Explosion(west.get(i), Direction.LEFT,this,1,4);
        }




    }
    public ArrayList<Coordinates> borderFacingPixelsCoordinates;
    public int getSize(){
        return 3;
    }


}

