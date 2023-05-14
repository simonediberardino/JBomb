package game.entity.blocks;

import game.entity.models.Entity;
import game.models.Coordinates;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class InvisibleBlock extends HardBlock{

    public InvisibleBlock(Coordinates coordinates){
        super(coordinates);
    }
    @Override
    protected void doInteract(Entity e) {
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage("assets/worlds/0/common/destroyable_block.png");
    }
}
