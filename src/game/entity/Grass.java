package game.entity;

import game.BomberMan;
import game.models.Coordinates;

import java.awt.*;

public class Grass extends Block{
    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage(){
        return loadAndSetImage(BomberMan.getInstance().getCurrentLevel().getGrassBlock());
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
    public void spawn(){}
}
