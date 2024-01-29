package game.engine.level.levels.world2

import game.engine.world.entity.impl.enemies.npcs.FastEnemy
import game.engine.world.entity.impl.enemies.npcs.TankEnemy
import game.engine.world.entity.impl.models.Enemy
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World2levelInfo

class World2Level3 : StoryLevel() {
    override val info: LevelInfo
        get() = object: World2levelInfo(this) {
            override val levelId: Int get() = 3
            override val startEnemiesCount: Int get() = 13
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(TankEnemy::class.java, FastEnemy::class.java)
            override val nextLevel: Class<out Level?> get() = World2Level4::class.java
        }
}