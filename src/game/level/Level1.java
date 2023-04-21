package game.level;

import game.entity.Coordinates;
import game.entity.Grass;
import game.entity.StoneBlock;
import game.ui.GridImage;

import javax.swing.*;

import java.awt.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public class Level1 extends Level{
    @Override
    public String getStone() {
        return "assets/level_1_stone.png";
    }

    @Override
    public String getGrass() {
        return "assets/level_1_grass.png";
    }

    @Override
    public String getDestroyable() {
        return null;
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(114, 175, 72);
    }

    @Override
    public Icon getPitch() {
        return null;
    }

    public void generateStone(JComponent[][] positions){
        for(int i = 0; i < positions.length/BLOCK_SIZE/2; i++){
            for(int j = 0; j < positions[i].length/BLOCK_SIZE/2; j++){
                new StoneBlock(new Coordinates(i*BLOCK_SIZE*2+BLOCK_SIZE, j*BLOCK_SIZE*2+BLOCK_SIZE)).spawn();

            }
        }
    }
}
