package game.entity.enemies.npcs;

import game.models.Coordinates;
import game.models.Direction;
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
        Direction d = getImageDirection();
        String basePath = getBasePath();
        String[] skins = new String[Direction.values().length];

        for (int i = 0; i < skins.length; i++) {
            skins[i] = String.format("%s_%s_%d.png", basePath, d.toString().toLowerCase(), i);
        }

        return skins;
    }

    private Direction getImageDirection() {
        if (imagePossibleDirections.contains(currDirection)) {
            return currDirection;
        }

        if (imagePossibleDirections.contains(previousDirection)) {
            return previousDirection;
        }

        return imagePossibleDirections.get(0);
    }


    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
