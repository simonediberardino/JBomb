package game.powerups;

import game.data.DataInputOutput;
import game.entity.models.BomberEntity;
import game.entity.models.Coordinates;
import game.events.ExplosionLengthPowerUpEvent;
import game.utils.Paths;

import java.awt.image.BufferedImage;

import static game.entity.bomb.AbstractExplosion.MAX_EXPLOSION_LENGTH;

public class IncreaseMaxBombsPowerUp extends PowerUp{

    /**
     * Constructs a PowerUp entity with the specified coordinates.
     *
     * @param coordinates the coordinates of the PowerUp entity
     */
    public IncreaseMaxBombsPowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage(Paths.getPowerUpsFolder() + "/max_bombs_powerup.png");
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    protected void doApply(BomberEntity entity) {
        DataInputOutput.increaseMaxBombs();
    }

    @Override
    protected void cancel(BomberEntity entity) {
    }

    @Override
    public boolean pickUpLimit(BomberEntity entity) {
        return DataInputOutput.getMaxBombs() > BomberEntity.MAX_BOMB_CAN_HOLD;
    }


}
