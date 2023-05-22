package game.entity.models;


import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.ui.panels.game.PitchPanel;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Block extends Entity {
    public final static int SIZE = PitchPanel.GRID_SIZE;

    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
    @Override
    public void eliminated(){
        destroy();
    }
    public void destroy(){
        despawn();
    }

    @Override
    public int getDrawPriority() {
        return 0;
    }
}
