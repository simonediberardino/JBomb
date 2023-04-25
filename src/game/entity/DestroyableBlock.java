package game.entity;

import game.BomberMan;
import game.models.Coordinates;

import java.awt.*;


public class DestroyableBlock extends Block{
    public DestroyableBlock(Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    public void interact(Entity e) {

    }

    @Override
    public Image getImage(){
        return loadAndSetImage(BomberMan.getInstance().getCurrentLevel().getDestroyableBlock());
    }

}
