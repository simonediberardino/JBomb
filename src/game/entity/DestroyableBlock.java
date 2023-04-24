package game.entity;

import game.BomberMan;
import game.ui.UIHandler;

import javax.swing.*;


public class DestroyableBlock extends Block{
    public DestroyableBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void interact(Entity e) {

    }

    @Override
    public Icon[] getIcon() {
        return new Icon[0];
    }


}
