package game.level.world1

import game.entity.enemies.npcs.YellowBall
import game.entity.models.Enemy
import game.level.Level

class World1Level1 : World1Level() {
    override val levelId: Int
        get() {
            return LEVEL_ID
        }

    override val startEnemiesCount: Int
        get() {
            return 7
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    YellowBall::class.java
            )
        }

    override val nextLevel: Class<out Level?>
        get() {
            return World1Level2::class.java
        }

    companion object {
        const val LEVEL_ID = 1
    }
}