package game.level.world2

import game.entity.enemies.npcs.FastEnemy
import game.entity.enemies.npcs.TankEnemy
import game.entity.models.Enemy
import game.level.Level

class World2Level4 : World2Level() {
    override val levelId: Int
        get() {
            return 4
        }

    override val startEnemiesCount: Int
        get() {
            return 15
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    FastEnemy::class.java,
                    TankEnemy::class.java
            )
        }

    override val nextLevel: Class<out Level?>
        get() {
            return World2Level5::class.java
        }
}