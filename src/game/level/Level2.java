package game.level;

import game.entity.enemies.npcs.FlyingEnemy;
import game.entity.enemies.npcs.TankEnemy;
import game.entity.enemies.npcs.YellowBall;
import game.entity.models.Enemy;

/**

 Represents the second level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 2, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class Level2 extends Level {
    public Level2() {
        super(2);
    }

    @Override
    public int startEnemiesCount() {
        return 0;
    }

    @Override
    public int getMaxDestroyableBlocks(){
        return 10;
    }

    @Override
    public int getExplosionLength() {
        return explosionLength;
    }

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[]{
                YellowBall.class,
                FlyingEnemy.class,
                TankEnemy.class
        };
    }
}
