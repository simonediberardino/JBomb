package game.level;

import game.BomberMan;
import game.entity.Enemy;
import game.models.Coordinates;
import game.entity.StoneBlock;
import game.ui.GameFrame;

import javax.swing.*;

import java.awt.*;

import static game.ui.GamePanel.GRID_SIZE;

/**

 Represents the first level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 1, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class Level1 extends Level {

    @Override
    public void spawnEnemies() {
        new Enemy(new Coordinates(120, 300)).spawn();

    }

    @Override
    public int startEnemiesCount() {
        return 8;
    }

    @Override
    public int getMaxBombs() {
        return 1;
    }

    /**

     Returns the path to the image file for the stone block in level 1.
     @return a string representing the path to the image file.
     */
    @Override
    public String getStoneBlock() {
        return "assets/level_1_stone.png";
    }
    /**

     Returns the path to the image file for the grass block in level 1.
     @return a string representing the path to the image file.
     */
    @Override
    public String getGrassBlock() {
        return "assets/level_1_grass.png";
    }
    /**

     Returns null because level 1 does not have any destroyable blocks.
     @return null
     */
    @Override
    public String getDestroyableBlock() {
        return null;
    }
    /**

     Returns the explosion length for level 1.
     @return an integer representing the explosion length.
     */
    @Override
    public int getExplosionLength() {
        return 1;
    }

    @Override
    public Icon getPitch() {
        return null;
    }
    /**

     Generates the stone blocks in the game board for level 1.

     @param jPanel the JPanel where the stone blocks are to be placed.
     */
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
