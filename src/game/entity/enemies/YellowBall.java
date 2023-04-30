package game.entity.enemies;

import game.BomberMan;
import game.entity.models.Enemy;
import game.models.Coordinates;
import game.ui.Paths;

import static java.text.MessageFormat.format;

public class YellowBall extends Enemy {
    public YellowBall(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball";
    }

    @Override
    public String[] getFrontIcons() {
        return new String[] {
                String.format("%s_%s.png", getBasePath(), 0),
                String.format("%s_%s.png", getBasePath(), 1),
                String.format("%s_%s.png", getBasePath(), 2),
                String.format("%s_%s.png", getBasePath(), 3),
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
}
