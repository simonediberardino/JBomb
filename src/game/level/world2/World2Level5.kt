package game.level.world2

import game.entity.Player
import game.entity.enemies.boss.Boss
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.npcs.Eagle
import game.entity.enemies.npcs.FastEnemy
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.level.Level
import game.level.WorldSelectorLevel
import game.ui.panels.game.PitchPanel
import java.awt.Dimension

class World2Level5 : World2Level() {
    override val levelId: Int
        get() {
            return 5
        }

    override val boss: Boss
        get() {
            return Clown()
        }

    override val startEnemiesCount: Int
        get() {
            return 7
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    FastEnemy::class.java,
                    Eagle::class.java)
        }

    override val isLastLevelOfWorld: Boolean
        get() {
            return true
        }

    override val nextLevel: Class<out Level?>
        get() {
            return WorldSelectorLevel::class.java
        }

    override val playerSpawnCoordinates: Coordinates
        get() {
            return Coordinates.roundCoordinates(Coordinates(0, 0), Player.SPAWN_OFFSET)
        }
}