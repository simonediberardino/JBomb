package game.powerups;

import game.entity.Player;
import game.entity.models.*;
import game.entity.models.Character;
import game.models.Coordinates;
import game.ui.GamePanel;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class PowerUp extends EntityInteractable {
    public static final Class<? extends PowerUp>[] POWER_UPS = new Class[] {
/*
            ArmorPowerUp.class,
*/
            FirePowerUp.class,
/*            SpeedPowerUp.class,
            TransparentBombsPowerUp.class,
            TransparentDestroyableBlocksPowerUp.class*/
    };

    public static final int DEFAULT_DURATION_SEC = 15;
    private Character character;
    protected boolean applied = false;

    public static Class<? extends PowerUp> getRandomPowerUpClass() {
        return POWER_UPS[new Random().nextInt(POWER_UPS.length)];
    }

    public static PowerUp spawnRandomPowerUp(Coordinates coordinates) {
        try {
            PowerUp powerUp = getRandomPowerUpClass().getConstructor(Coordinates.class).newInstance(coordinates);
            powerUp.spawn();
            return powerUp;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    protected abstract void doApply(BomberEntity entity);
    protected abstract void cancel(BomberEntity entity);

    @Override
    public int getSize() {
        return GamePanel.COMMON_DIVISOR * 4;
    }

    public final void apply(BomberEntity entity) {
        if (applied || !isSpawned()) {
            return;
        }

        this.applied = true;
        this.despawn();
        this.character = entity;
        this.doApply(entity);

        int duration = getDuration() * 1000;

        if(duration > 0) {
            TimerTask explodeTask = new TimerTask() {
                public void run() {
                    PowerUp.this.cancel(entity);
                }
            };

            Timer timer = new Timer();
            timer.schedule(explodeTask, duration);
        }
    }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities() {
        return new HashSet<>(Collections.singletonList(Player.class));
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return Collections.emptySet();
    }
}
