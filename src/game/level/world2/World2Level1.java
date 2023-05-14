package game.level.world2;

import game.entity.enemies.boss.Boss;
import game.entity.enemies.npcs.FlyingEnemy;
import game.entity.enemies.npcs.TankEnemy;
import game.entity.enemies.npcs.YellowBall;
import game.entity.enemies.npcs.Zombie;
import game.entity.models.Enemy;
import game.level.Level;
import game.level.world1.World1Level2;

/**

 Represents the second level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 2, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class World2Level1 extends World2Level {
    @Override
    public int getLevelId() {
        return 1;
    }
    @Override
    public int startEnemiesCount() {
        return 13;
    }

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[]{
                FlyingEnemy.class,
                TankEnemy.class
        };
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return World2Level2.class;
    }
}
