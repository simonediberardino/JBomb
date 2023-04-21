package game.level;

import game.entity.Coordinates;
import game.entity.Grass;
import game.ui.GridImage;

import javax.swing.*;

import java.awt.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public abstract class Level {
    public abstract String getStone();
    public abstract String getGrass();
    public abstract String getDestroyable();

    public Icon[] getStoneBlock(){
        return new GridImage(getStone(), BLOCK_SIZE).generate();
    }

    public Icon[] getDestroyableBlock(){
        return new GridImage(getDestroyable(), BLOCK_SIZE).generate();
    }

    public Icon[] getGrassBlock(){
        return new GridImage(getGrass(), BLOCK_SIZE).generate();
    }

    public abstract Icon getPitch();
    public abstract Color getBackgroundColor();
    public void generateGrass(JComponent[][] positions) {
        for (int i = 0; i < positions.length/BLOCK_SIZE; i++) {
            for(int j = 0; j < positions[i].length/BLOCK_SIZE; j++){
                new Grass(new Coordinates(i*BLOCK_SIZE, j*BLOCK_SIZE)).spawn();
            }
        }
    }

    public abstract void generateStone(JComponent[][] positions);


    public void generatePitch(JComponent[][] positions){
        generateGrass(positions);
        generateStone(positions);
    }
}
