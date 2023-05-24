package game.powerups;

import game.Bomberman;
import game.entity.blocks.MovableBlock;
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
        return loadAndSetImage("assets/powerups/hand.png");
    }

    @Override
    public int getDuration() {
        return 30;
    }

    @Override
    protected void doApply(BomberEntity entity) {
        entity.addClassInteractWithMouseDrag(MovableBlock.class);
    }

    @Override
    protected void cancel(BomberEntity entity) {
        entity.removeClassInteractWithDrag(MovableBlock.class);
    }
}
