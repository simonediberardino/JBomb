package game.level;

import game.BomberMan;
import game.entity.blocks.DestroyableBlock;
import game.entity.enemies.YellowBall;
import game.entity.models.Enemy;
import game.models.Coordinates;
import game.entity.blocks.StoneBlock;

import javax.swing.*;

import static game.ui.GamePanel.GRID_SIZE;

/**

 Represents the first level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 1, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class Level1 extends Level {
    public Level1() {
        super(1);
    }

    @Override
    public void spawnEnemies() {
        for(int i = 0; i < startEnemiesCount(); i++)
            new YellowBall(Coordinates.generateRandomCoordinates()).spawn();
    }

    @Override
    public int startEnemiesCount() {
        return 8;
    }

    @Override
    public int getMaxBombs() {
        return 1;
    }
    public int getMaxDestroyableBlocks(){
        return 10;
    }

    /**

     Returns the explosion length for level 1.
     @return an integer representing the explosion length.
     */
    @Override
    public int getExplosionLength() {
        return 3;
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
    @Override
    public void generateDestroyableBlock(){
        DestroyableBlock block = new DestroyableBlock(new Coordinates(0,0));
        int i = 0;
        while (i <getMaxDestroyableBlocks()){
            if (!block.isSpawned()){
                block.setCoords(Coordinates.generateRandomCoordinates());
                block.spawn();
            }
            else {
                block = new DestroyableBlock(new Coordinates(0,0));
                i++;
            }
        }
    }
}
