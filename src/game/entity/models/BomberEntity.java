package game.entity.models;

import game.Bomberman;
import game.entity.Player;
import game.entity.bomb.Bomb;
import game.powerups.PowerUp;
import game.sound.SoundModel;
import game.utils.Utility;

import java.util.*;

public abstract class BomberEntity extends Character {
    public static final int MAX_BOMB_CAN_HOLD = 10;
    private final List<Class<? extends Entity>> listInteractWithMouseClick = new ArrayList<>();
    private final List<Class<? extends Entity>> listInteractWithMouseDrag = new ArrayList<>();
    private final List<Class<? extends PowerUp>> activePowerUp = new ArrayList<>();
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
        return Bomberman.getMatch().getCurrentLevel().getMaxBombs();
    }

    @Override
    protected SoundModel getStepSound() {
        return null;
    }

    protected void placeBomb() {
        if(getCurrExplosionLength() == 0) {
            return;
        }

        if(placedBombs >= getCurrBombLimit()) {
            return;
        }

        if(Utility.timePassed(lastPlacedBombTime) < Bomb.PLACE_INTERVAL){
            return;
        }

        lastPlacedBombTime = System.currentTimeMillis();
        placedBombs++;

        Bomb bomb = new Bomb(this);

        if(this instanceof Player){
            Bomberman.getMatch().getInventoryElementControllerBombs().setNumItems(getCurrentBombs());
        }

        bomb.setOnExplodeListener(() -> {
            placedBombs--;
            if(this instanceof Player){
                Bomberman.getMatch().getInventoryElementControllerBombs().setNumItems(getCurrentBombs());
            }
        });

        bomb.spawn(true);
        bomb.trigger();
    }

    public int getCurrentBombs() {
        return getCurrBombLimit() - placedBombs;
    }

    public void addClassInteractWithMouseClick(Class<? extends Entity> cls) {
        listInteractWithMouseClick.add(cls);
    }
    public void addClassInteractWithMouseDrag(Class<? extends Entity> cls) {
        listInteractWithMouseDrag.add(cls);
    }

    public void removeClassInteractWithMouse(Class<? extends Entity> cls) {
        listInteractWithMouseClick.remove(cls);
    }
    public void removeClassInteractWithDrag(Class<? extends Entity> cls) {
        listInteractWithMouseDrag.remove(cls);
    }

    public List<Class<? extends PowerUp>> getActivePowerUps() {
        return activePowerUp;
    }

    public void removeActivePowerUp(PowerUp p){
        getActivePowerUps().removeIf(e->e.isInstance(p));
    }

    public List<Class<? extends Entity>> getListClassInteractWithMouseClick(){
        return listInteractWithMouseClick;
    }
    public List<Class<? extends Entity>> getListClassInteractWithMouseDrag(){
        return listInteractWithMouseDrag;
    }
}
