package game.entity;

import game.ui.UIHandler;

import javax.swing.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public abstract class Block extends Entity{
    public Block(Coordinates coordinates) {
        super(coordinates);
    }
}
