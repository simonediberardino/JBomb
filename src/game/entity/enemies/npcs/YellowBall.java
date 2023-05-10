package game.entity.enemies.npcs;

import game.models.Coordinates;
import game.ui.panels.game.PitchPanel;
import game.utils.Paths;

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
    public String[] getBaseSkins() {
        return new String[]{
                String.format("%s_%s.png", getBasePath(), 0),
                String.format("%s_%s.png", getBasePath(), 1),
                String.format("%s_%s.png", getBasePath(), 2),
                String.format("%s_%s.png", getBasePath(), 3),
        };
    }

    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
