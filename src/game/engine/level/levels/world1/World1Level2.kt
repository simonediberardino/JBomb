package game.engine.level.levels.world1

import game.engine.world.entity.impl.enemies.npcs.Helicopter
import game.engine.world.entity.impl.models.Enemy
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World1LevelInfo

class World1Level2 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 2
            override val startEnemiesCount: Int get() = 7
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(Helicopter::class.java)
            override val nextLevel: Class<out Level?> get() = World1Level3::class.java
        }
}