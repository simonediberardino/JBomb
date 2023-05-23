package game.entity.enemies.npcs;

import game.entity.enemies.npcs.IntelligentEnemy;
import game.models.Coordinates;
import game.utils.Paths;

public class Zombie extends IntelligentEnemy {
    public Zombie() {
        super();
    }

    public Zombie(Coordinates coordinates) {
        super(coordinates);
        setHp(300);
    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{ String.format("%s/zombie/fast_enemy.png", Paths.getEnemiesFolder()) };
    }
}
