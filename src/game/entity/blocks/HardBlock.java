package game.entity.blocks;

import game.entity.bomb.Explosion;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.models.Coordinates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class HardBlock extends Block {
    public HardBlock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected Set<Class<? extends Entity>> getBasePassiveInteractionEntities() {
        return new HashSet<>();
    }
}
