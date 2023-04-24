package game.entity;

import game.BomberMan;
import game.models.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class StoneBlock extends Block {
    public StoneBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void interact(Entity e) {

    }

    @Override
    public Image getImage(){
        return BomberMan.getInstance().getCurrentLevel().getStoneBlock();
    }

}
