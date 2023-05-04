package game.level;

import game.BomberMan;
import game.entity.enemies.FlyingEnemy;
import game.entity.enemies.TankEnemy;
import game.models.Coordinates;
import game.powerups.ArmorPowerUp;
import game.powerups.SpeedPowerUp;
import game.powerups.TransparentDestroyableBlocksPowerUp;

import static game.ui.GamePanel.GRID_SIZE;

/**

 Represents the second level of the game, extending the abstract class Level.

 Provides specific implementations of methods for level 2, including images for the blocks,

 explosion length, and a method to generate the stone blocks in the game board.
 */
public class Level2 extends Level {
    public Level2() {
        super(2);
    }

    @Override
    public void spawnEnemies() {
        for(int i = 0; i < startEnemiesCount(); i++) {
            new FlyingEnemy(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
            new TankEnemy(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3)).spawn();
        }
    }

    @Override
    public int startEnemiesCount() {
        return 3;
    }

    @Override
    public int getMaxDestroyableBlocks(){
        return 10;
    }

    @Override
    public int getExplosionLength() {
        return 1;
    }
}
