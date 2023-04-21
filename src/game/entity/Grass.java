package game.entity;

import game.BomberMan;

import javax.swing.*;

public class Grass extends Block{
    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    Icon[] getIcon() {
        return BomberMan.getInstance().getCurrentLevel().getGrassBlock();
    }

    @Override
    public void spawn(){
        if(!isSpawned()) {
            setPosition(false, getCoords().getX(), getCoords().getY());
            setSpawned(true);
        }
    }
}
