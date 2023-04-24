package game.entity;

import game.BomberMan;
import game.models.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Grass extends Block{
    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage(){
        return BomberMan.getInstance().getCurrentLevel().getGrassBlock();
    }


    @Override
    public void spawn(){
        if(!isSpawned()) {
            move(getCoords().getX(), getCoords().getY());
            setSpawned(true);
        }
    }

    @Override
    public void interact(Entity e) {

    }
}
