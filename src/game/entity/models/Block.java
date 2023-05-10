package game.entity.models;


import game.models.Coordinates;
import game.ui.panels.game.PitchPanel;

public abstract class Block extends Entity {
    public final static int SIZE = PitchPanel.GRID_SIZE;

    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
    public void destroy(){
        despawn();
    }

    @Override
    public int getDrawPriority() {
        return 0;
    }
}
