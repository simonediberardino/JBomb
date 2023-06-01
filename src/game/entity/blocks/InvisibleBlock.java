package game.entity.blocks;

import game.entity.models.Entity;
import game.entity.models.Coordinates;

import java.awt.image.BufferedImage;

public class InvisibleBlock extends HardBlock{

    public InvisibleBlock(Coordinates coordinates){
        super(coordinates);
    }

    @Override
    protected void doInteract(Entity e) {}

    @Override
    public BufferedImage getImage() {
        return null;
    }
}
