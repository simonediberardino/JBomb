package game.powerups;

import game.Bomberman;
import game.data.DataInputOutput;
import game.entity.models.*;
import game.entity.models.Coordinates;
import game.events.ExplosionLengthPowerUpEvent;
import game.utils.Paths;

import java.awt.image.BufferedImage;

import static game.entity.bomb.Explosion.MAX_EXPLOSION_LENGTH;

public class FirePowerUp extends PowerUp {
    /**
     * Constructs an entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */

    public FirePowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage(Paths.getPowerUpsFolder() + "/fire_up.png");
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    protected void doApply(BomberEntity entity) {
        entity.increaseExplosionLength();
        new ExplosionLengthPowerUpEvent().invoke(this);
    }

    @Override
    protected void cancel(BomberEntity entity) {
    }

    @Override
    public boolean pickUpLimit(BomberEntity entity) {
        return DataInputOutput.getExplosionLength()>MAX_EXPLOSION_LENGTH ;
    }
}
