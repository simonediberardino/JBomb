package game.entity.enemies.npcs;

import game.entity.blocks.DestroyableBlock;
import game.entity.models.*;
import game.models.Coordinates;
import game.utils.Paths;

import java.util.HashSet;
import java.util.Set;

public class FlyingEnemy extends IntelligentEnemy {
    public FlyingEnemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/flying_enemy";
    }

    public String[] getBaseSkins() {
        return new String[]{
                getBasePath() + "/eagle_front_0.png",
                getBasePath() + "/eagle_front_1.png",
                getBasePath() + "/eagle_front_2.png",
        };
    }

    @Override
    public String[] getLeftIcons() {
        return new String[]{
                getBasePath() + "/eagle_left_0.png",
                getBasePath() + "/eagle_left_1.png",
                getBasePath() + "/eagle_left_2.png",
        };
    }

    @Override
    public String[] getBackIcons() {
        return new String[]{
                getBasePath() + "/eagle_back_0.png",
                getBasePath() + "/eagle_back_1.png",
                getBasePath() + "/eagle_back_2.png",
        };
    }

    @Override
    public String[] getRightIcons() {
        return new String[]{
                getBasePath() + "/eagle_right_0.png",
                getBasePath() + "/eagle_right_1.png",
                getBasePath() + "/eagle_right_2.png",
        };
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        Set<Class<? extends Entity>> baseObstacles = new HashSet<>(super.getObstacles());
        baseObstacles.remove(DestroyableBlock.class);
        return baseObstacles;
    }
}
