package game.entity.enemies.npcs;

import game.entity.models.Coordinates;
import game.entity.models.Direction;
import game.ui.panels.game.PitchPanel;
import game.utils.Paths;

import java.util.Arrays;
import java.util.List;

public class YellowBall extends IntelligentEnemy {
    public YellowBall() {
        super();
    }

    public YellowBall(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball";
    }

    @Override
    protected List<Direction> getImageDirections() {
        return Arrays.asList(Direction.RIGHT, Direction.LEFT);
    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{
                String.format("%s/yellow_ball/yellow_ball_%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 0),
                String.format("%s/yellow_ball/yellow_ball_%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 1),
                String.format("%s/yellow_ball/yellow_ball_%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 2),
                String.format("%s/yellow_ball/yellow_ball_%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 3)
        };
    }

    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
