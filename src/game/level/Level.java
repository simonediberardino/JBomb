package game.level;

import game.BomberMan;
import game.entity.*;
import game.models.Direction;
import game.ui.GridImage;

import javax.swing.*;

import java.awt.*;

import static game.ui.UIHandler.GRID_SIZE;


public abstract class Level {
    public abstract String getStone();
    public abstract String getGrass();
    public abstract String getDestroyable();

    public Icon[] getStoneBlock(){
        return new GridImage(getStone(), GRID_SIZE).generate();
    }

    public Icon[] getDestroyableBlock(){
        return new GridImage(getDestroyable(), GRID_SIZE).generate();
    }

    public Icon[] getGrassBlock(){
        return new GridImage(getGrass(), GRID_SIZE).generate();
    }

    public abstract Icon getPitch();
    public abstract Color getBackgroundColor();

    public void generateGrass(JComponent[][] positions) {
        for (int i = 0; i < positions.length / GRID_SIZE; i++)
            for (int j = 0; j < positions[i].length / GRID_SIZE; j++)
                new Grass(new Coordinates(i * GRID_SIZE, j * GRID_SIZE)).spawn();
    }

    public abstract void generateStone(JComponent[][] positions);


    public void generatePitch(JComponent[][] positions){
        generateGrass(positions);
        generateStone(positions);

    }
}
