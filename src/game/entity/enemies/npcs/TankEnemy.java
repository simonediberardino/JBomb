package game.entity.enemies.npcs;

import game.entity.Player;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.entity.models.*;
import game.models.Coordinates;
import game.models.Direction;
import game.utils.Paths;

import java.util.*;

public class TankEnemy extends IntelligentEnemy implements Explosive {
    private static final int STANDING_STILL_PERIOD = 1000;
    private static final int PROBABILITY_OF_SHOOTING = 30;
    private static final int SHOOTING_REFRESH_RATE = 2000;
    private boolean canShoot = false;
    private long lastUpdate = 0;

    public TankEnemy() {
        super();
    }

    public TankEnemy(Coordinates coordinates) {
        super(coordinates);
    }

    public String[] getBaseSkins() {
        return new String[]{
                Paths.getEnemiesFolder() + "/tank/tank_front.png"
        };
    }


    @Override
    public String[] getLeftIcons() {
        return new String[]{
                Paths.getEnemiesFolder() + "/tank/tank_left.png"
        };
    }

    @Override
    public String[] getBackIcons() {
        return new String[]{
                Paths.getEnemiesFolder() + "/tank/tank_back.png"
        };
    }

    @Override
    public String[] getRightIcons() {
        return new String[]{
                Paths.getEnemiesFolder() + "/tank/tank_right.png"
        };
    }

    @Override
    public void doUpdate(boolean arg) {
        if (System.currentTimeMillis() - lastUpdate > SHOOTING_REFRESH_RATE) {
            lastUpdate = System.currentTimeMillis();

            if (canShoot && Math.random() * 100 < PROBABILITY_OF_SHOOTING) {
                // explosion offset only used on vertical directions
                Coordinates newCoords = getNewTopLeftCoordinatesOnDirection(currDirection, Explosion.SIZE);
                if(currDirection == Direction.UP || currDirection == Direction.DOWN){
                    int x = newCoords.getX() + Explosion.spawnOffset;
                    newCoords = new Coordinates(x,newCoords.getY());
                }
                new Explosion(newCoords, currDirection, this);
                canMove = false;
            }
            canShoot = true;
        }

        if (System.currentTimeMillis() - lastUpdate > STANDING_STILL_PERIOD) {
            canMove = true;
        }

        super.doUpdate(arg);
    }

    @Override
    public List<Class<? extends Entity>> getExplosionObstacles() {
        return Collections.emptyList();
    }

    @Override
    public boolean isObstacleOfExplosion(Entity e) {
        return (e == null) || (getExplosionObstacles().stream().anyMatch(c -> c.isInstance(e)));
    }

    @Override
    public List<Class<? extends Entity>> getExplosionInteractionEntities() {
        return Arrays.asList(Player.class, Bomb.class);
    }

    @Override
    public boolean canExplosionInteractWith(Entity e) {
        return e == null || (getExplosionInteractionEntities().stream().anyMatch(c -> c.isInstance(e)));
    }

    @Override
    public int getMaxExplosionDistance() {
        return 4;
    }


}
