package game.level.world2

import game.entity.enemies.npcs.Eagle
import game.entity.enemies.npcs.FastEnemy
import game.entity.models.Enemy
import game.level.Level
import game.level.StoryLevel
import game.level.info.model.LevelInfo
import game.level.info.imp.World2levelInfo

class World2Level1 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World2levelInfo(this) {
            override val levelId: Int
                get() {
                    return 1
                }

            override val startEnemiesCount: Int
                get() {
                    return 7
                }

            override val availableEnemies: Array<Class<out Enemy>>
                get() {
                    return arrayOf(
                            Eagle::class.java,
                            FastEnemy::class.java
                    )
                }

            override val nextLevel: Class<out Level?>
                get() {
                    return World2Level2::class.java
                }
        }

}