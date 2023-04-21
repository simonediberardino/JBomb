package game.entity;

import game.BomberMan;
import game.ui.GridImage;
import game.ui.UIHandler;

import javax.swing.*;

import static game.ui.UIHandler.BLOCK_SIZE;

public class Enemy extends Character {
    public Enemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    Icon[] getIcon() {
        return new GridImage("assets/enemy.png", BLOCK_SIZE).generate();
    }
}
