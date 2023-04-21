package game.entity;

import game.BomberMan;
import game.ui.GridImage;
import game.ui.UIHandler;

import javax.swing.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public class StoneBlock extends Block {
    public StoneBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    Icon[] getIcon() {
        return BomberMan.getInstance().getCurrentLevel().getStoneBlock();
    }


}
