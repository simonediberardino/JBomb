package game.entity.enemies;

import game.entity.bomb.Explosion;
import game.entity.models.Enemy;
import game.entity.models.Explosive;
import game.models.Coordinates;

import java.util.Observable;

public class TankEnemy extends Enemy implements Explosive {
    private boolean canShoot = false;
    private long lastUpdate = 0;
    private final int probabilityOfShooting =30;
    private final int shootingRefreshRate = 10000;
    private final int standingStillPeriod = 1000;

    public TankEnemy(Coordinates coordinates) {
        super(coordinates);
    }
    protected String getBasePath() {
        return "";
    }

    public String[] getFrontIcons() {
        return new String[] {
                "assets/entities/enemies/Tank/tank.png"
        };
    }

    @Override
    public String[] getLeftIcons() {
        return getFrontIcons();
    }

    @Override
    public String[] getBackIcons() {
        return getFrontIcons();
    }

    @Override
    public String[] getRightIcons() {
        return getFrontIcons();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(System.currentTimeMillis() - lastUpdate > shootingRefreshRate){
            lastUpdate = System.currentTimeMillis();
            if(canShoot && Math.random()*100<probabilityOfShooting) {
                new Explosion(getNewTopLeftCoordinatesOnDirection(currDirection, Explosion.SIZE), currDirection, this);
                canMove = false;
            }
            canShoot= true;
        }
        if (System.currentTimeMillis()-lastUpdate> 1000) {
            canMove = true;

        }super.update(o, arg);
    }

}
