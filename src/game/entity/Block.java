package game.entity;


import game.models.Coordinates;
import game.ui.GameFrame;
import game.ui.Utility;

public abstract class Block extends Entity{
    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return GameFrame.GRID_SIZE;
    }
}
