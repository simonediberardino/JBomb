package game.entity.enemies.boss;

import game.entity.enemies.npcs.IntelligentEnemy;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.panels.PitchPanel;

import java.util.HashSet;
import java.util.Set;

public abstract class Boss extends IntelligentEnemy {
    public Boss(Coordinates coordinates){
        super(coordinates);
        super.setHp(1000);
        setAttackDamage(1000);
    }

    @Override
    public int getSize() {
        return  PitchPanel.GRID_SIZE * 4;
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return new HashSet<>();
    }
}
