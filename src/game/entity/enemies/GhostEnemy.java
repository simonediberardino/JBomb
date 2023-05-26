package game.entity.enemies;

import game.entity.enemies.npcs.IntelligentEnemy;
import game.entity.models.Entity;
import game.models.Direction;
import game.ui.panels.game.PitchPanel;
import game.utils.Paths;

import java.util.HashSet;
import java.util.Set;

public class GhostEnemy extends IntelligentEnemy {
    public GhostEnemy(){
        hitboxSizetoWidthRatio = 0.837f;
        hitboxSizeToHeightRatio= 1;
        imagePossibleDirections.remove(Direction.UP);
        imagePossibleDirections.remove(Direction.DOWN);
    }
    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{String.format("%s/mini_ghost/ghost_%s.png", Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase())};

    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return new HashSet<>();
    }
    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
