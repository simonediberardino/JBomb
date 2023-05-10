package game.level.world2;

import game.entity.enemies.boss.Boss;
import game.entity.enemies.npcs.FlyingEnemy;
import game.entity.enemies.npcs.YellowBall;
import game.entity.models.Enemy;
import game.level.Level;
import game.level.world1.World1Level;

public class World2Level4 extends World2Level {
    public World2Level4() {
        super(4);
    }

    @Override
    public int startEnemiesCount() {
        return 12;
    }

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[]{
                YellowBall.class,
                FlyingEnemy.class
        };
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return World2Level5.class;
    }
}