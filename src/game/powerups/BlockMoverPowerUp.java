package game.powerups;

import game.entity.models.BomberEntity;
import game.models.Coordinates;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class BlockMoverPowerUp extends PowerUp{
    public BlockMoverPowerUp(Coordinates coordinates){
        super(coordinates);
        incompatiblePowerUps.add(Hammer.class);
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    protected void doApply(BomberEntity entity) {

    }

    @Override
    protected void cancel(BomberEntity entity) {

    }
}
