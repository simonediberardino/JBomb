package game.entity.enemies;

import game.entity.blocks.DestroyableBlock;
import game.entity.models.*;
import game.models.Coordinates;
import game.ui.Paths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlyingEnemy extends Enemy {
    public FlyingEnemy(Coordinates coordinates) {
        super(coordinates);
    }

    public String[] getFrontIcons() {
        return new String[]{
                Paths.getEnemiesFolder() + "/flying_enemy/aquila.png"
        };
    }

    @Override
    public String[] getLeftIcons() {
        return getFrontIcons();
    }

    @Override
    public String[] getBackIcons() {
        return getFrontIcons();
    }

    @Override
    public String[] getRightIcons() {
        return getFrontIcons();
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        Set<Class<? extends Entity>> baseObstacles = new HashSet<>(super.getObstacles());
        baseObstacles.remove(DestroyableBlock.class);
        return baseObstacles;
    }


}
