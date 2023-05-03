package game.entity.models;

import game.BomberMan;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.models.Coordinates;

import java.util.Arrays;
import java.util.List;

import static game.ui.GamePanel.GRID_SIZE;

public abstract class BomberEntity extends Character {
    private int maxBombs = BomberMan.getInstance().getCurrentLevel().getMaxBombs();
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

    public int getMaxBombs() {
        return maxBombs;
    }

    public void increaseMaxBombs() {
        maxBombs++;
    }

    protected void placeBomb() {
        if(placedBombs >= maxBombs) {
            return;
        }

        if(System.currentTimeMillis() - lastPlacedBombTime < Bomb.PLACE_INTERVAL){
            return;
        }

        lastPlacedBombTime = System.currentTimeMillis();
        placedBombs++;

        Coordinates coordinates = new Coordinates(
                ((getCoords().getX() + getSize()/2) / GRID_SIZE * GRID_SIZE + Bomb.spawnOffset.getX())
                ,(getCoords().getY() + getSize()/2) / GRID_SIZE * GRID_SIZE + (Bomb.spawnOffset.getY())
        );

        Bomb bomb = new Bomb(coordinates);

        bomb.setOnExplodeListener(() -> placedBombs--);
        bomb.spawn();
        bomb.trigger();
    }

    @Override
    public List<Class<? extends Entity>> getInteractionsEntities() {
        return Arrays.asList(StoneBlock.class, DestroyableBlock.class, Explosion.class);
    }
}
