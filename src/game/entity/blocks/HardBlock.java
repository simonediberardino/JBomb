package game.entity.blocks;

import game.entity.bomb.Explosion;
import game.entity.models.Block;
import game.models.Coordinates;

import java.util.Arrays;
import java.util.HashSet;

public abstract class HardBlock extends Block {
    public HardBlock(Coordinates coordinates) {
        super(coordinates);

    }

    @Override
    public void setPassiveInteractionEntities() {
        passiveInteractionEntities = new HashSet<>();
    }
}
