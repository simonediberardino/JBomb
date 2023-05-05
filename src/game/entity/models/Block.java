package game.entity.models;


import game.models.Coordinates;
import game.panels.PitchPanel;

public abstract class Block extends Entity {
    public final static int SIZE = PitchPanel.GRID_SIZE;

    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
