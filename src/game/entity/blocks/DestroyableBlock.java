package game.entity.blocks;

import game.BomberManMatch;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.powerups.PowerUp;

import java.awt.image.BufferedImage;


public class DestroyableBlock extends Block {
    public DestroyableBlock(Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    protected void doInteract(Entity e) {
    }

    @Override
    public BufferedImage getImage(){
        return loadAndSetImage(BomberManMatch.getInstance().getCurrentLevel().getDestroyableBlock());
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        if(Math.random() > 1/4f) PowerUp.spawnRandomPowerUp(this.getCoords());
    }
}
