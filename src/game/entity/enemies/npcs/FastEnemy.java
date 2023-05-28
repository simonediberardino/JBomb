package game.entity.enemies.npcs;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.panels.game.PitchPanel;
import game.utils.Paths;

import java.util.Arrays;
import java.util.List;

public class FastEnemy extends IntelligentEnemy {
    public FastEnemy() {
        super();
    }

    public FastEnemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/fast_enemy/fast_enemy";
    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{
                String.format("%s_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 0),
                String.format("%s_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 1),
                String.format("%s_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 2),
                String.format("%s_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 3),
        };
    }

    @Override
    public float getSpeed() {
        return 2f;
    }

    @Override
    protected List<Direction> getImageDirections() {
        return Arrays.asList(Direction.RIGHT, Direction.LEFT);
    }

    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
