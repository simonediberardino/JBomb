package game.powerups;

import game.entity.Player;
import game.entity.models.*;
import game.entity.models.Character;
import game.models.Coordinates;
import game.ui.panels.game.PitchPanel;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The abstract PowerUp class is a superclass for all power-ups in the game.
 */
public abstract class PowerUp extends EntityInteractable {
    // A static array of power-up classes
    public static final Class<? extends PowerUp>[] POWER_UPS = new Class[] {
            /*ArmorPowerUp.class,
            FirePowerUp.class,
            SpeedPowerUp.class,
            TransparentBombsPowerUp.class,
            TransparentDestroyableBlocksPowerUp.class*/
            EndLevelPowerUp.class
    };

    // The default duration for a power-up, in seconds
    public static final int DEFAULT_DURATION_SEC = 15;

    // The character the power-up is applied to
    private Character character;

    // Whether the power-up has already been applied or not
    protected boolean applied = false;

    /**
     * Returns a random power-up class from the POWER_UPS array.
     *
     * @return a random power-up class
     */
    public static Class<? extends PowerUp> getRandomPowerUpClass() {
        return POWER_UPS[new Random().nextInt(POWER_UPS.length)];
    }

    /**
     * Spawns a random power-up at the specified coordinates.
     *
     * @param coordinates the coordinates to spawn the power-up at
     * @return the spawned power-up
     */
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
     * Constructs a PowerUp entity with the specified coordinates.
     *
     * @param coordinates the coordinates of the PowerUp entity
     */
    public PowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Returns the duration of the power-up in milliseconds.
     *
     * @return the duration of the power-up
     */
    public abstract int getDuration();

    /**
     * Applies the power-up to the specified BomberEntity.
     *
     * @param entity the BomberEntity to apply the power-up to
     */
    public final void apply(BomberEntity entity) {
        if (applied || !isSpawned()) {
            return;
        }

        this.applied = true;
        this.despawn();
        this.character = entity;
        this.doApply(entity);

        int duration = getDuration() * 1000;

        // If the power-up has a duration, schedule a TimerTask to cancel it when the duration is up
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

    /**
     * Applies the power-up to the specified BomberEntity. This method should be implemented by the subclasses.
     *
     * @param entity the BomberEntity to apply the power-up to
     */
    protected abstract void doApply(BomberEntity entity);

    /**
     * Cancels the power-up applied to the specified BomberEntity. This method should be implemented by the subclasses.
     *
     * @param entity the BomberEntity to cancel the power-up for
     */
    protected abstract void cancel(BomberEntity entity);

    /**
     * Returns the size of the PowerUp entity.
     *
     * @return the size of the PowerUp entity
     */
    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }

    @Override
    protected void doInteract(Entity e) {
        this.apply((BomberEntity) e);
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
