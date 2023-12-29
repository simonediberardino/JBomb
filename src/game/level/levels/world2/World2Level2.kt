package game.level.levels.world2

import game.entity.enemies.npcs.Eagle
import game.entity.enemies.npcs.TankEnemy
import game.entity.models.Enemy
import game.level.levels.Level
import game.level.levels.StoryLevel
import game.level.info.model.LevelInfo
import game.level.info.imp.World2levelInfo

class World2Level2 : StoryLevel() {
    override val info: LevelInfo
        get() = object: World2levelInfo(this) {
            override val levelId: Int get() = 2
            override val startEnemiesCount: Int get() = 8
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(TankEnemy::class.java, Eagle::class.java)
            override val nextLevel: Class<out Level?> get() = World2Level3::class.java
        }
}