package game.entity.models;

import game.models.Coordinates;

public abstract class EntityDamage extends EntityInteractable {
    /**
     * Constructs an interactive entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    public EntityDamage(Coordinates coordinates) {
        super(coordinates);
    }
}
