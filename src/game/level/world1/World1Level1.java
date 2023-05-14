package game.level.world1;

import game.entity.enemies.boss.Boss;
import game.entity.enemies.npcs.FlyingEnemy;
import game.entity.enemies.npcs.YellowBall;
import game.entity.models.Enemy;
import game.level.Level;
import game.level.world2.World2Level;

/**

 Represents the first level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 1, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class World1Level1 extends World1Level {
    public static final int LEVEL_ID = 1;

    @Override
    public int getLevelId() {
        return LEVEL_ID;
    }

    @Override
    public int startEnemiesCount() {
        return 5;
    }

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[]{
                YellowBall.class
        };
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return World1Level2.class;
    }
}
