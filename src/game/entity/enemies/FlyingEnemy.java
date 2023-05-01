package game.entity.enemies;

import game.entity.blocks.DestroyableBlock;
import game.entity.models.Block;
import game.entity.models.Enemy;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.Paths;

public class FlyingEnemy extends Enemy {
    public FlyingEnemy(Coordinates coordinates) {
        super(coordinates);
    }
    protected String getBasePath() {
        return "";
    }

    public String[] getFrontIcons() {
        return new String[] {
                "assets/entities/player/player_front_0.png"
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
    public boolean isObstacle(Entity e) {
        if (e instanceof Block && !(e instanceof DestroyableBlock)) {
            return true;
        }
        return false;
    }
}
