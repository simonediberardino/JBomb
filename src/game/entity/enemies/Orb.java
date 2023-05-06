package game.entity.enemies;

import game.entity.Particle;
import game.entity.Player;
import game.entity.models.Enemy;
import game.entity.models.Entity;
import game.entity.models.EntityDamage;
import game.models.Coordinates;
import game.models.Direction;
import game.models.EnhancedDirection;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class Orb extends Enemy implements Particle {
    EnhancedDirection direction;
    public Orb(Coordinates coordinates, EnhancedDirection direction) {
        super(coordinates);
        this.direction = direction;
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage("assets/bomb/flame_central0.png");
    }

    @Override
    public String[] getFrontIcons() {
        return new String[0];
    }

    @Override
    public String[] getLeftIcons() {
        return new String[0];
    }

    @Override
    public String[] getBackIcons() {
        return new String[0];
    }

    @Override
    public String[] getRightIcons() {
        return new String[0];
    }

    @Override
    protected void doInteract(Entity e) {

        if (canInteractWith(e)&&e!=null) {
            attack(e);
        }
        if (isObstacle(e)) {
            despawn();

        }




    }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities() {
        return new HashSet<Class<?extends Entity>>(Arrays.asList(Player.class));
    }
    @Override
    public boolean isObstacle(Entity e){
        return e instanceof Player ||e==null;
    }



    public void update(boolean gameState) {
        if (canMove && gameState&& isSpawned()){
            for (Direction d:
                 direction.toDirection()
                 ) {moveOrInteract(d);

            }


        }

    }

}
