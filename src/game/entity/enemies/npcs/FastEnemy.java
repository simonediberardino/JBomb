package game.entity.enemies.npcs;

import game.models.Coordinates;

// TODO
public class FastEnemy extends IntelligentEnemy {
    public FastEnemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String[] getBaseSkins() {
        return new String[]{
                "assets/entities/enemies/yellow_ball/yellow_ball_0.png"
        };
    }

    @Override
    public float getSpeed() {
        return 1.5f;
    }
}
