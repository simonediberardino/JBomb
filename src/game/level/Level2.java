package game.level;

import game.BomberMan;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.enemies.FlyingEnemy;
import game.entity.enemies.TankEnemy;
import game.entity.enemies.YellowBall;
import game.models.Coordinates;
import game.powerups.MaxBombsPowerUp;

import javax.swing.*;

import static game.ui.GamePanel.GRID_SIZE;

/**

 Represents the second level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 2, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class Level2 extends Level {
    private int maxBombs = 2;
    private int explosionLength = 1;

    public Level2() {
        super(2);
    }

    @Override
    public void spawnEnemies() {
        for(int i = 0; i < startEnemiesCount(); i++) {
            new YellowBall(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
            new FlyingEnemy(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
            new TankEnemy(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();

            new MaxBombsPowerUp(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
        }
    }

    @Override
    public int startEnemiesCount() {
        return 3;
    }

    @Override

    public int getMaxDestroyableBlocks(){
        return 10;
    }

    public int getMaxBombs() {
        return maxBombs;
    }


    public void increaseMaxBombs(){
        if(explosionLength<=10) maxBombs++;

    }

    /**

     Returns the explosion length for level 1.
     @return an integer representing the explosion length.
     */
    @Override
    public int getExplosionLength() {
        return explosionLength;
    }

    public void increaseExplosionLength(){
        if(explosionLength<=10) explosionLength++;
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
                block.setCoords(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE*2));
                block.spawn();
            }
            else {
                block = new DestroyableBlock(new Coordinates(0,0));
                i++;
            }
        }
    }
}
