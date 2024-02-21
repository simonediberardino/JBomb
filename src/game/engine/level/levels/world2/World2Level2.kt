package game.engine.level.levels.world2

import game.engine.world.domain.entity.actors.impl.enemies.npcs.Eagle
import game.engine.world.domain.entity.actors.impl.enemies.npcs.TankEnemy
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World2levelInfo

class World2Level2 : StoryLevel() {
    override val info: LevelInfo
        get() = object: World2levelInfo(this) {
            override val levelId: Int get() = 2
            override val startEnemiesCount: Int get() = 8
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(TankEnemy::class.java, Eagle::class.java)
            override val nextLevel: Class<out Level?> get() = World2Level3::class.java
        }
}