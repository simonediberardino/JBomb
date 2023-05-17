package game.entity.enemies.npcs;

import game.entity.blocks.DestroyableBlock;
import game.entity.models.*;
import game.models.Coordinates;
import game.utils.Paths;

import java.util.HashSet;
import java.util.Set;

public abstract class FlyingEnemy extends IntelligentEnemy {
    public FlyingEnemy() {
        super();
    }

    public FlyingEnemy(Coordinates coordinates) {
        super(coordinates);
    }


    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        Set<Class<? extends Entity>> baseObstacles = new HashSet<>(super.getObstacles());
        baseObstacles.remove(DestroyableBlock.class);
        return baseObstacles;
    }
}
