package game.entity.enemies.npcs;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.panels.game.PitchPanel;
import game.utils.Paths;

public class FastEnemy extends IntelligentEnemy {
    public FastEnemy() {
        super();
        imagePossibleDirections.remove(Direction.UP);
        imagePossibleDirections.remove(Direction.DOWN);
    }

    public FastEnemy(Coordinates coordinates) {
        super(coordinates);
        imagePossibleDirections.remove(Direction.UP);
        imagePossibleDirections.remove(Direction.DOWN);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/fast_enemy/fast_enemy";
    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{
                String.format("%s/fast_enemy/fast_enemy%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 0),
                String.format("%s/fast_enemy/fast_enemy%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 1),
                String.format("%s/fast_enemy/fast_enemy%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 2),
                String.format("%s/fast_enemy/fast_enemy%s_%d.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), 3)
        };
    }

    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
