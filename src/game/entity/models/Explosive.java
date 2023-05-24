package game.entity.models;

import game.entity.bomb.Explosion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface Explosive {
    List<Class<? extends Entity>> getExplosionObstacles();
    List<Explosion> getExplosions();

    boolean isObstacleOfExplosion(Entity e);

    List<Class<? extends Entity>> getExplosionInteractionEntities();

    boolean canExplosionInteractWith(Entity e);
    int getMaxExplosionDistance();
}
