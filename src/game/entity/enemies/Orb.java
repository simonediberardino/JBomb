package game.entity.enemies;

import game.entity.Particle;
import game.entity.Player;
import game.entity.Transparent;
import game.entity.bomb.Bomb;
import game.entity.models.Enemy;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;
import game.models.EnhancedDirection;
import game.panels.PitchPanel;
import game.utils.Paths;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Orb extends Enemy implements Transparent, Particle{
    public static final int SIZE = PitchPanel.COMMON_DIVISOR * 4;
    //only one field between can be instantiated at a time
    EnhancedDirection enhancedDirection = null;
    Direction direction = null;

    public Orb(Coordinates coordinates, EnhancedDirection enhancedDirection) {
        super(coordinates);
        this.enhancedDirection = enhancedDirection;
    }

    public Orb(Coordinates coordinates, Direction direction) {
        super(coordinates);
        this.direction = direction;
    }

    @Override
    public String[] getFrontIcons() {
        return new String[]{
                Paths.getAssetsFolder() + "/bomb/flame_central0.png"
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
    public int getSize() {
        return SIZE;
    }

    @Override
    protected void doInteract(Entity e) {
        if (canInteractWith(e)) {
            attack(e);
        }

        if (isObstacle(e)) {
            despawn();
        }
    }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities() {
        return new HashSet<>(Arrays.asList(Player.class, Bomb.class));
    }

    @Override
    public boolean isObstacle(Entity e){
        return e==null;
    }



    public void update(boolean gameState) {
        if (enhancedDirection != null) {
            if (canMove && gameState && isSpawned()) {
                for (Direction d :
                        enhancedDirection.toDirection()
                ) {
                   moveOrInteract(d);

                }


            }

        }else if (canMove&&gameState&&isSpawned()){
            moveOrInteract(direction);
        }
    }

}
