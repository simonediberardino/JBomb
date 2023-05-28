package game.powerups;

import game.entity.models.*;
import game.entity.models.Coordinates;
import game.utils.Paths;

import java.awt.image.BufferedImage;

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
    }

    @Override
    protected void cancel(BomberEntity entity) {

    }
}
