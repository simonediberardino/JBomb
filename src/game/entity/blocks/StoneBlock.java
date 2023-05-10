package game.entity.blocks;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.models.Entity;
import game.models.Coordinates;

import java.awt.image.BufferedImage;


public class StoneBlock extends HardBlock {
    public StoneBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return null;
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    protected void doInteract(Entity e) {
        if(e == null) return;
    }

    @Override
    public BufferedImage getImage(){
        return loadAndSetImage(Bomberman.getMatch().getCurrentLevel().getStoneBlock());
    }

}
