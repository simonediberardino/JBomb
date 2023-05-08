package game.entity.enemies;

import game.entity.Player;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.entity.models.Entity;
import game.entity.models.Explosive;
import game.models.Coordinates;
import game.models.Direction;
import game.models.EnhancedDirection;
import game.panels.PitchPanel;
import game.utils.Paths;

import java.awt.image.BufferedImage;
import java.util.*;

public class Boss extends IntelligentEnemy implements Explosive {
    public Boss(Coordinates coordinates){
        super(coordinates);
        super.setHp(1000);
        setAttackDamage(1000);
    }

    @Override
    public float getImageRatio(){
        return 1;
    }

    @Override
    public int getSize() {
        return  PitchPanel.GRID_SIZE*4;
    }

    @Override
    public Set<Class<? extends Entity>> getObstacles() {
        return new HashSet<>();
    }

    @Override
    public String[] getFrontIcons() {
        return new String[]{
                Paths.getEnemiesFolder() + "/clown/clown_with_hat.png"
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
    public boolean isObstacleOfExplosion(Entity e) {
        return (e == null) || (getExplosionObstacles().stream().anyMatch(c -> c.isInstance(e)));
    }

    @Override
    public List<Class<? extends Entity>> getExplosionObstacles() {
        return Collections.emptyList();
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
        return 10;
    }

    // TODO Refactor
    @Override
    public void update(boolean gamestate){
        if(Math.random()*100>99){
/*            for (Direction d: Direction.values()) {
                new Orb(Coordinates.fromDirectionToCoordinateOnEntity(this,d, Orb.SIZE,Orb.SIZE),d).spawn(true,false);
            }
            for (EnhancedDirection d: EnhancedDirection.values()) {
                new Orb(Coordinates.fromDirectionToCoordinateOnEntity(this,d,Orb.SIZE),d).spawn(true, false);
            }*/
        }
        if(Math.random()*100>99){
            Direction[] dir = Direction.values();
            LinkedList<Direction> directions = new LinkedList<Direction>(Arrays.asList(dir));
            directions.remove(Direction.UP);
            Direction d = directions.get((int) (Math.random()*directions.size()));
            int inwardOffset = getSize()/4;
            int parallelOffset = - Explosion.SIZE/2;

            switch (d){
                case RIGHT:case LEFT:parallelOffset = 0; inwardOffset = getSize()/3-PitchPanel.GRID_SIZE/2;

            }

            new Explosion(Coordinates.fromDirectionToCoordinateOnEntity(this,d, inwardOffset,parallelOffset,Explosion.SIZE),d,this);
        }

       super.update(gamestate);
    }
}
