package game.entity;

import game.BomberMan;
import game.ui.GridImage;
import game.ui.UIHandler;

import javax.swing.*;


public class StoneBlock extends Block {
    public StoneBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void interact(Entity e) {

    }

    @Override
    Icon[] getIcon() {
        return BomberMan.getInstance().getCurrentLevel().getStoneBlock();
    }


}
