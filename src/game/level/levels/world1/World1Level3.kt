package game.level.levels.world1

import game.entity.enemies.npcs.Helicopter
import game.entity.enemies.npcs.YellowBall
import game.entity.models.Enemy
import game.level.levels.Level
import game.level.levels.StoryLevel
import game.level.info.model.LevelInfo
import game.level.info.imp.World1LevelInfo

class World1Level3 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 3
            override val startEnemiesCount: Int get() = 7
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(YellowBall::class.java, Helicopter::class.java)
            override val nextLevel: Class<out Level?> get() = World1Level4::class.java
        }
}