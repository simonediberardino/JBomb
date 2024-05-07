package game.domain.level.levels.world2

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.domain.world.domain.entity.actors.impl.enemies.npcs.eagle.Eagle
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastPurpleBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.ArenaLevel
import game.domain.level.levels.Level
import game.domain.level.info.model.DefaultArenaLevelInfo
import game.domain.level.info.model.LevelInfo
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.SkeletonEnemy

class World2Arena : ArenaLevel() {
    override val info: LevelInfo
        get() = object : DefaultArenaLevelInfo(this) {
            override val specialRoundEnemies: Array<Class<out Enemy?>> get() = arrayOf(TankEnemy::class.java, SkeletonEnemy::class.java)
            override val boss: Boss get() = Clown()
            override val maxDestroyableBlocks: Int get() = 10
            override val nextLevel: Class<out Level>? get() = null
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(
                    FastPurpleBall::class.java,
                    Eagle::class.java,
                    SkeletonEnemy::class.java
            )
            override val worldId: Int get() = 2
            override val levelId: Int get() = 0
        }
}