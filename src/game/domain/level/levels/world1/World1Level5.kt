package game.domain.level.levels.world1

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.domain.world.domain.entity.actors.impl.enemies.npcs.helicopter.Helicopter
import game.domain.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.YellowBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.levels.StoryLevel
import game.domain.level.info.model.LevelInfo
import game.domain.level.info.imp.World1LevelInfo
import game.domain.level.levels.world2.World2Level1
import game.presentation.ui.panels.game.PitchPanel
import java.awt.Dimension

class World1Level5 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 5
            override val boss: Boss get() = GhostBoss()
            override val startEnemiesCount: Int get() = 0
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(YellowBall::class.java, Zombie::class.java)
            override val isLastLevelOfWorld: Boolean get() = true
            override val nextLevel: Class<out Level?> get() = World2Level1::class.java
            override val playerSpawnCoordinates: Coordinates
                get() = Coordinates.fromRowAndColumnsToCoordinates(
                            Dimension(0,
                                    PitchPanel.DIMENSION.getHeight().toInt() / PitchPanel.GRID_SIZE - 1)
                    )
        }
}