package game.level;

import game.BomberMan;
import game.models.Coordinates;
import game.entity.StoneBlock;
import game.ui.GameFrame;

import javax.swing.*;

import java.awt.*;

import static game.ui.GameFrame.GRID_SIZE;


public class Level1 extends Level {
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
    public Icon getPitch() {
        return null;
    }

    @Override
    public void generateStone(JPanel jPanel) {
        int currX = 0;
        int currY = GRID_SIZE;

        while (currY < jPanel.getHeight() - GRID_SIZE) {
            while (currX < jPanel.getWidth() - GRID_SIZE && currX + GRID_SIZE * 2 <= jPanel.getWidth()) {
                currX += GRID_SIZE;
                new StoneBlock(new Coordinates(currX, currY)).spawn();
                currX += GRID_SIZE;
            }
            currX = 0;
            currY += GRID_SIZE * 2;
        }
    }
}
