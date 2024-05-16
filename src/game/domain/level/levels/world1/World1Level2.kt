package game.domain.level.levels.world1

import game.domain.world.domain.entity.actors.impl.enemies.npcs.helicopter.Helicopter
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.levels.StoryLevel
import game.domain.level.info.model.LevelInfo
import game.domain.level.info.imp.World1LevelInfo

class World1Level2 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 2
            override val startEnemiesCount: Int get() = 5
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(Helicopter::class.java)
            override val nextLevel: Class<out Level?> get() = World1Level3::class.java
        }
}