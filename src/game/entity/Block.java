package game.entity;

import game.ui.UIHandler;

import javax.swing.*;


public abstract class Block extends Entity{
    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return 5;
    }
}
