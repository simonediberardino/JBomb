package game.engine.level.levels.world2

import game.engine.world.domain.entity.actors.impl.enemies.npcs.Eagle
import game.engine.world.domain.entity.actors.impl.enemies.npcs.FastEnemy
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World2levelInfo

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