package game.entity;

import game.BomberMan;
import game.ui.UIHandler;

import javax.swing.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public class DestroyableBlock extends Block{
    public DestroyableBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Icon[] getIcon() {
        return new Icon[0];
    }


}
