package game.engine.level.levels.world1

import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.engine.world.domain.entity.actors.impl.enemies.npcs.Helicopter
import game.engine.world.domain.entity.actors.impl.enemies.npcs.TankEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.YellowBall
import game.engine.world.domain.entity.actors.impl.enemies.npcs.Zombie
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.ArenaLevel
import game.engine.level.levels.Level
import game.engine.level.info.model.DefaultArenaLevelInfo
import game.engine.level.info.model.LevelInfo

class World1Arena : ArenaLevel() {
    override val info: LevelInfo
        get() = object : DefaultArenaLevelInfo(this) {
            override val specialRoundEnemies: Array<Class<out Enemy?>> = arrayOf(TankEnemy::class.java, Zombie::class.java)
            override val boss: Boss get() = GhostBoss()
            override val maxDestroyableBlocks: Int get() = 10
            override val nextLevel: Class<out Level?>? get() = null
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(
                        YellowBall::class.java,
                        Helicopter::class.java,
                        Zombie::class.java
                )
            override val worldId: Int get() = 1
            override val levelId: Int get() = 0
        }

}