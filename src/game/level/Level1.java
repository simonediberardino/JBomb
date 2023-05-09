package game.level;

import game.entity.enemies.npcs.YellowBall;
import game.entity.models.Enemy;

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
    public int startEnemiesCount() {
        return 0;
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

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[]{
                YellowBall.class
        };
    }
}
