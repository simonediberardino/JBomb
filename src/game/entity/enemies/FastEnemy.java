package game.entity.enemies;

import game.entity.models.Enemy;
import game.models.Coordinates;

import java.util.Observable;

// TODO
public class FastEnemy extends Enemy {
    public FastEnemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String[] getFrontIcons() {
        return new String[]{"assets/entities/enemies/yellow_ball/yellow_ball_0.png"};
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
    public float getSpeed() {
        return 1.5f;
    }
}
