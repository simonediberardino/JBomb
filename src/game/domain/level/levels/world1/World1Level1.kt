package game.domain.level.levels.world1

import game.domain.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.YellowBall
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.levels.StoryLevel
import game.domain.level.info.model.LevelInfo
import game.domain.level.info.imp.World1LevelInfo

class World1Level1 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 1
            override val startEnemiesCount: Int get() = 4
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(YellowBall::class.java)
            override val nextLevel: Class<out Level?> get() = World1Level2::class.java
        }
}