package game.level.world2

import game.entity.enemies.npcs.FastEnemy
import game.entity.enemies.npcs.TankEnemy
import game.entity.models.Enemy
import game.level.Level
import game.level.StoryLevel
import game.level.info.model.LevelInfo
import game.level.info.imp.World2levelInfo

class World2Level4 : StoryLevel() {
    override val info: LevelInfo
        get() = object: World2levelInfo(this) {
            override val levelId: Int get() = 4
            override val startEnemiesCount: Int get() = 15
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(FastEnemy::class.java, TankEnemy::class.java)
            override val nextLevel: Class<out Level?> get() = World2Level5::class.java
        }
}