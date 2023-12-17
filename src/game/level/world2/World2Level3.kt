package game.level.world2

import game.entity.enemies.npcs.FastEnemy
import game.entity.enemies.npcs.TankEnemy
import game.entity.models.Enemy
import game.level.Level

class World2Level3 : World2Level() {
    override val levelId: Int
        get() {
            return 3
        }

    override val startEnemiesCount: Int
        get() {
            return 13
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    TankEnemy::class.java,
                    FastEnemy::class.java)
        }

    override val nextLevel: Class<out Level?>
        get() {
            return World2Level4::class.java
        }
}