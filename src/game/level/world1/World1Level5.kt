package game.level.world1

import game.entity.enemies.boss.Boss
import game.entity.enemies.boss.ghost.GhostBoss
import game.entity.enemies.npcs.Helicopter
import game.entity.enemies.npcs.YellowBall
import game.entity.enemies.npcs.Zombie
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.level.Level
import game.level.world2.World2Level1
import game.ui.panels.game.PitchPanel
import java.awt.Dimension

class World1Level5 : World1Level() {
    override val levelId: Int
        get() {
            return 5
        }

    override val boss: Boss
        get() {
            return GhostBoss()
        }

    override val startEnemiesCount: Int
        get() {
            return 0
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    YellowBall::class.java,
                    Helicopter::class.java,
                    Zombie::class.java
            )
        }

    override val isLastLevelOfWorld: Boolean
        get() {
            return true
        }

    override val nextLevel: Class<out Level?>
        get() {
            return World2Level1::class.java
        }

    override val playerSpawnCoordinates: Coordinates
        get() {
            return Coordinates.fromRowAndColumnsToCoordinates(Dimension(0, PitchPanel.DIMENSION.getHeight().toInt() / PitchPanel.GRID_SIZE - 1))
        }
}