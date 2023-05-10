package game.entity.enemies.boss;

import game.entity.enemies.npcs.IntelligentEnemy;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.panels.PitchPanel;

import java.awt.image.BufferedImage;
import java.util.*;

public abstract class Boss extends IntelligentEnemy {
    protected static int SIZE = PitchPanel.GRID_SIZE * 4;
    protected int currRageStatus = 0;
    protected TreeMap<Integer, Integer> healthStatusMap = new TreeMap<>(healthStatusMap());

    public Boss(Coordinates coordinates){
        super(coordinates);
        super.setMaxHp(1000);
        super.setHp(getMaxHp());
        super.setAttackDamage(1000);
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return new HashSet<>();
    }

    protected String getImageFromRageStatus() {
        return getImagePath();
    }

    protected abstract Map<Integer, Integer> healthStatusMap();

    protected abstract void updateRageStatus(int status);
}
