package game.entity.models;

import game.models.Coordinates;

public abstract class MovingEntity extends EntityDamage{
    public MovingEntity(Coordinates coordinates){
        super(coordinates);
    }

}
