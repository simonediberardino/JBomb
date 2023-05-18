package game.entity.models;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.powerups.PowerUp;
import game.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public abstract class BomberEntity extends Character {
    private static final int MAX_BOMB_CAN_HOLD = 10;
    private final ArrayList<Class<? extends Entity>> listInteractWithMouse = new ArrayList<>(Collections.emptyList());
    private final ArrayList<Class<? extends PowerUp>> activePowerUp = new ArrayList<>();
    private int currBombLimit = Bomberman.getMatch().getCurrentLevel().getMaxBombs();
    private int currExplosionLength = Bomberman.getMatch().getCurrentLevel().getExplosionLength();
    private int placedBombs = 0;
    private long lastPlacedBombTime = 0;

    /**
     * Constructs a new Character with the specified Coordinates.
     *
     * @param coordinates the coordinates of the new Character
     */
    public BomberEntity(Coordinates coordinates) {
        super(coordinates);

    }

    public int getCurrExplosionLength(){
        return currExplosionLength;
    }

    public void increaseExplosionLength() {
        currExplosionLength++;
    }

    public int getCurrBombLimit() {
        return currBombLimit;
    }

    public void increaseMaxBombs() {
        if(currBombLimit < MAX_BOMB_CAN_HOLD) currBombLimit++;
    }

    protected void placeBomb() {
        if(placedBombs >= currBombLimit) {
            return;
        }

        if(Utility.timePassed(lastPlacedBombTime) < Bomb.PLACE_INTERVAL){
            return;
        }

        lastPlacedBombTime = System.currentTimeMillis();
        placedBombs++;

        Bomb bomb = new Bomb(this);

        bomb.setOnExplodeListener(() -> placedBombs--);
        bomb.spawn(true);
        bomb.trigger();
    }

    public void addClassInteractWithMouse(Class<? extends Entity> cls) {
        listInteractWithMouse.add(cls);
    }

    public void removeClassInteractWithMouse(Class<? extends Entity> cls) {
        listInteractWithMouse.remove(cls);
    }

    public ArrayList<Class<? extends PowerUp>> getActivePowerUps() {
        return activePowerUp;
    }

    public ArrayList<Class<? extends Entity>> getListClassInteractWithMouse(){
        return listInteractWithMouse;
    }
}
