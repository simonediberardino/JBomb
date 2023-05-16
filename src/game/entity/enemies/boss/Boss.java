package game.entity.enemies.boss;

import game.Bomberman;
import game.entity.enemies.npcs.IntelligentEnemy;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.powerups.PowerUp;
import game.powerups.portal.EndLevelPortal;
import game.ui.panels.game.PitchPanel;

import java.util.*;

import static game.ui.panels.game.PitchPanel.GRID_SIZE;

public abstract class Boss extends IntelligentEnemy {
    protected static int SIZE = GRID_SIZE * 4;
    protected int currRageStatus = 0;
    protected TreeMap<Integer, Integer> healthStatusMap = new TreeMap<>(healthStatusMap());

    public Boss(Coordinates coordinates){
        super(coordinates);
        super.setMaxHp(1000);
        super.setHp(getMaxHp());
        super.setAttackDamage(1000);
    }

    @Override
    public int getDrawPriority() {
        return 20;
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

    @Override
    protected void onDespawn() {
        super.onDespawn();
        PowerUp endLevelPortal = new EndLevelPortal(Coordinates.generateCoordinatesAwayFromPlayer());
        endLevelPortal.spawn(true,true);
    }

    protected abstract void updateRageStatus(int status);
}
