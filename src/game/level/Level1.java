package game.level;

import game.BomberMan;
import game.models.Coordinates;
import game.entity.StoneBlock;
import game.ui.GameFrame;

import javax.swing.*;

import java.awt.*;

import static game.ui.GameFrame.GRID_SIZE;

/**

 Represents the first level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 1, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class Level1 extends Level {

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
        return 2;
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
