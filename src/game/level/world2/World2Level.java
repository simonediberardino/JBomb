package game.level.world2;

import game.entity.enemies.boss.Boss;
import game.entity.enemies.npcs.FlyingEnemy;
import game.entity.enemies.npcs.TankEnemy;
import game.entity.enemies.npcs.YellowBall;
import game.entity.models.Enemy;
import game.level.Level;

public abstract class World2Level extends Level {
    public World2Level(int id) {
        super(id, 2);
    }

    @Override
    public final int getMaxDestroyableBlocks() {
        return 15;
    }

    @Override
    public final int getExplosionLength() {
        return getLevelId();
    }

    public final int getMaxBombs() {
        return 3;
    }

    @Override
    public Boss getBoss() {
        return null;
    }
}
