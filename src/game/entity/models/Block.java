package game.entity.models;


import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.GamePanel;

public abstract class Block extends Entity {
    public Block(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getSize() {
        return GamePanel.GRID_SIZE;
    }
    @Override
    public void spawn(){

        super.spawn();
    }
}
