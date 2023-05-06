package game.entity.enemies;

import game.entity.models.Enemy;
import game.models.Coordinates;

public class Zombie extends IntelligentEnemy {
    public Zombie(Coordinates coordinates) {
        super(coordinates);
        setHp(300);
    }

    @Override
    public String[] getFrontIcons() {
        return new String[]{"assets/entities/enemies/zombie/fast_enemy.png"};
    }

    @Override
    public String[] getLeftIcons() {
        return new String[]{"assets/entities/enemies/zombie/fast_enemy.png"};
    }

    @Override
    public String[] getBackIcons() {
        return new String[]{"assets/entities/enemies/zombie/fast_enemy.png"};
    }

    @Override
    public String[] getRightIcons() {
        return new String[]{"assets/entities/enemies/zombie/fast_enemy.png"};    }
}
