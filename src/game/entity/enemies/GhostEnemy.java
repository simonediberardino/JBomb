package game.entity.enemies;

import game.entity.enemies.npcs.IntelligentEnemy;
import game.utils.Paths;

public class GhostEnemy extends IntelligentEnemy {
    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{Paths.getEnemiesFolder() + "/mini_ghost/ghost.png"};
    }
}
