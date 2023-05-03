package game.entity.enemies;

import game.entity.blocks.DestroyableBlock;
import game.entity.models.*;
import game.models.Coordinates;
import game.ui.Paths;

import java.util.ArrayList;
import java.util.List;

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
    public List<Class<? extends Entity>> getObstacles() {
        List<Class<? extends Entity>> baseObstacles = new ArrayList<>(super.getObstacles());
        baseObstacles.remove(DestroyableBlock.class);
        return baseObstacles;
    }


}
