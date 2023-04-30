package game.entity.blocks;

import game.BomberMan;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.models.Coordinates;

import java.awt.image.BufferedImage;


public class DestroyableBlock extends Block {
    public DestroyableBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return null;
    }

    @Override
    protected void onSpawn() {

    }

    @Override
    protected void onDespawn() {

    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    public void interact(Entity e) {
        if(e == null) return;
    }

    @Override
    public BufferedImage getImage(){
        return loadAndSetImage(BomberMan.getInstance().getCurrentLevel().getDestroyableBlock());
    }

}
