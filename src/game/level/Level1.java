package game.level;

import game.BomberMan;
import game.entity.blocks.DestroyableBlock;
import game.entity.enemies.YellowBall;
import game.entity.enemies.FlyingEnemy;
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
        for(int i = 0; i < startEnemiesCount(); i++) {
            new YellowBall(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
            new FlyingEnemy(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
        }
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
}
