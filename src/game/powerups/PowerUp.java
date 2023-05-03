package game.powerups;

import game.entity.models.*;
import game.entity.models.Character;
import game.models.Coordinates;
import game.ui.GamePanel;

import java.util.*;

public abstract class PowerUp extends InteractiveEntities {
    private Character character;
    /**
     * Constructs an entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    public PowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    public abstract int getDuration();

    @Override
    protected void doInteract(Entity e) {
        this.apply((BomberEntity) e);
    }

    @Override
    public int getSize() {
        return GamePanel.COMMON_DIVISOR * 4;
    }

    public void apply(BomberEntity entity) {
        this.despawn();
        this.character = entity;

        if(getDuration() > 0) {
            TimerTask explodeTask = new TimerTask() {
                public void run() {
                    PowerUp.this.cancel(entity);
                }
            };

            Timer timer = new Timer();
            timer.schedule(explodeTask, getDuration());
        }
    }

    @Override
    public List<Class<? extends Entity>> getInteractionsEntities() {
        return Collections.singletonList(BomberEntity.class);
    }

    @Override
    public List<Class<? extends Entity>> getObstacles() {
        return Collections.emptyList();
    }

    abstract void cancel(Character character);

}
