package game.level.world2

import game.entity.enemies.npcs.Eagle
import game.entity.enemies.npcs.TankEnemy
import game.entity.models.Enemy
import game.level.Level

class World2Level2 : World2Level() {
    override val levelId: Int
        get() {
            return 2
        }

    override val startEnemiesCount: Int
        get() {
            return 8
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    TankEnemy::class.java,
                    Eagle::class.java)
        }

    override val nextLevel: Class<out Level?>
        get() {
            return World2Level3::class.java
        }
}