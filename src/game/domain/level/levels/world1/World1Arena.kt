package game.domain.level.levels.world1

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.domain.world.domain.entity.actors.impl.enemies.npcs.helicopter.Helicopter
import game.domain.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.YellowBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.ArenaLevel
import game.domain.level.levels.Level
import game.domain.level.info.model.DefaultArenaLevelInfo
import game.domain.level.info.model.LevelInfo
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.SkeletonEnemy

class World1Arena : ArenaLevel() {
    override val info: LevelInfo
        get() = object : DefaultArenaLevelInfo(this) {
            override val specialRoundEnemies: Array<Class<out Enemy?>> = arrayOf(SkeletonEnemy::class.java, TankEnemy::class.java)
            override val boss: Boss get() = GhostBoss()
            override val maxDestroyableBlocks: Int get() = 10
            override val nextLevel: Class<out Level?>? get() = null
            override val availableEnemies: Array<Class<out Enemy>>
                get() = arrayOf(
                        Helicopter::class.java,
                        Zombie::class.java,
                        YellowBall::class.java,
                )
            override val worldId: Int get() = 1
            override val levelId: Int get() = 0
        }

}