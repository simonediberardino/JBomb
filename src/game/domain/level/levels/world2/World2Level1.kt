package game.domain.level.levels.world2

import game.domain.world.domain.entity.actors.impl.enemies.npcs.eagle.Eagle
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastEnemy
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.levels.StoryLevel
import game.domain.level.info.model.LevelInfo
import game.domain.level.info.imp.World2levelInfo

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