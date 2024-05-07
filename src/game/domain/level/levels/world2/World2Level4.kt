package game.domain.level.levels.world2

import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastPurpleBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.levels.StoryLevel
import game.domain.level.info.model.LevelInfo
import game.domain.level.info.imp.World2levelInfo

class World2Level4 : StoryLevel() {
    override val info: LevelInfo
        get() = object: World2levelInfo(this) {
            override val levelId: Int get() = 4
            override val startEnemiesCount: Int get() = 15
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(FastPurpleBall::class.java, TankEnemy::class.java)
            override val nextLevel: Class<out Level?> get() = World2Level5::class.java
        }
}