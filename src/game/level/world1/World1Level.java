package game.level.world1;

import game.entity.enemies.boss.Boss;
import game.entity.models.Enemy;
import game.level.Level;

public abstract class World1Level extends Level {
    public World1Level(int id) {
        super(id, 1);
    }

    @Override
    public Boss getBoss() {
        return null;
    }

    @Override
    public final int getMaxDestroyableBlocks() {
        return 10;
    }

    @Override
    public final int getExplosionLength() {
        return getLevelId();
    }

    @Override
    public final int getMaxBombs() {
        return 2;
    }
}
