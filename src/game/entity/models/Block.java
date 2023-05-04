package game.entity.models;


import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.GamePanel;

public abstract class Block extends Entity {
    public final static int SIZE = GamePanel.GRID_SIZE;

    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
