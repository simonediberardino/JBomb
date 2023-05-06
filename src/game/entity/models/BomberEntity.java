package game.entity.models;

import game.BomberMan;
import game.entity.bomb.Bomb;
import game.models.Coordinates;

public abstract class BomberEntity extends Character {
    private static final int MAX_BOMB_CAN_HOLD = 10;
    private int currBombLimit = BomberMan.getInstance().getCurrentLevel().getMaxBombs();
    private int currExplosionLength = BomberMan.getInstance().getCurrentLevel().getExplosionLength();

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

        if(System.currentTimeMillis() - lastPlacedBombTime < Bomb.PLACE_INTERVAL){
            return;
        }

        lastPlacedBombTime = System.currentTimeMillis();
        placedBombs++;

        Bomb bomb = new Bomb(this);

        bomb.setOnExplodeListener(() -> placedBombs--);
        bomb.spawn(true);
        bomb.trigger();
    }

}
