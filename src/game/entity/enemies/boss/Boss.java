package game.entity.enemies.boss;

import game.Bomberman;
import game.entity.enemies.npcs.IntelligentEnemy;
import game.entity.models.Entity;
import game.entity.models.Coordinates;
import game.powerups.PowerUp;
import game.powerups.portal.EndLevelPortal;

import java.awt.*;
import java.util.*;

import static game.ui.panels.game.PitchPanel.GRID_SIZE;

public abstract class Boss extends IntelligentEnemy {
    protected static int SIZE = GRID_SIZE * 4;
    protected int currRageStatus = 0;
    protected TreeMap<Integer, Integer> healthStatusMap = new TreeMap<>(healthStatusMap());

    public Boss() {
        this(null);

        Dimension panelSize = Bomberman
                .getBombermanFrame()
                .getPitchPanel()
                .getPreferredSize();
        setCoords(Coordinates.randomCoordinatesFromPlayer(getSize()));
    }

    public Boss(Coordinates coordinates){
        super(coordinates);
        super.setMaxHp(100);
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

    /**
     * Updates the rage status of the Boss, loading and setting the corresponding image.
     *
     * @param status the new rage status to be set.
     */
    protected void updateRageStatus(int status) {
        // If the new rage status is the same as the current one, nothing to update.
        if(status == currRageStatus) return;

        currRageStatus = status;
        // Get the corresponding image path from the current rage status.
        String imagePath = getImageFromRageStatus();
        // Load and set the image.
        loadAndSetImage(imagePath);
    }
}
