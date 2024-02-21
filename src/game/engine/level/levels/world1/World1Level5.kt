package game.engine.level.levels.world1

import game.engine.world.domain.entity.actors.impl.enemies.boss.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.engine.world.domain.entity.actors.impl.enemies.npcs.Helicopter
import game.engine.world.domain.entity.actors.impl.enemies.npcs.YellowBall
import game.engine.world.domain.entity.actors.impl.enemies.npcs.Zombie
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World1LevelInfo
import game.engine.level.levels.world2.World2Level1
import game.engine.ui.panels.game.PitchPanel
import java.awt.Dimension

class World1Level5 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 5
            override val boss: Boss get() = GhostBoss()
            override val startEnemiesCount: Int get() = 0
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(YellowBall::class.java, Helicopter::class.java, Zombie::class.java)
            override val isLastLevelOfWorld: Boolean get() = true
            override val nextLevel: Class<out Level?> get() = World2Level1::class.java
            override val playerSpawnCoordinates: Coordinates
                get() = Coordinates.fromRowAndColumnsToCoordinates(
                            Dimension(0,
                                    PitchPanel.DIMENSION.getHeight().toInt() / PitchPanel.GRID_SIZE - 1)
                    )
        }
}