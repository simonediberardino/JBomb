package game.level;

import game.entity.models.Enemy;
import game.models.Coordinates;

import javax.swing.*;

public class WorldSelectorLevel extends Level{
    public WorldSelectorLevel() {
        super(0);
    }

    @Override
    public int startEnemiesCount() {
        return 0;
    }

    @Override
    public int getMaxDestroyableBlocks() {
        return 0;
    }

    @Override
    public int getExplosionLength() {
        return 0;
    }

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[0];
    }

    @Override
    public void generateStone(JPanel jPanel) {
        generateWaterBlock();
    }

    @Override
    public void generateDestroyableBlock() {
    }

    @Override
    public void spawnEnemies() {
    }

    public void generateWaterBlock() {
    }
}
