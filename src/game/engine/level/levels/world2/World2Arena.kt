package game.engine.level.levels.world2

import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.engine.world.domain.entity.actors.impl.enemies.npcs.eagle.Eagle
import game.engine.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.ArenaLevel
import game.engine.level.levels.Level
import game.engine.level.info.model.DefaultArenaLevelInfo
import game.engine.level.info.model.LevelInfo

class World2Arena : ArenaLevel() {
    override val info: LevelInfo
        get() = object : DefaultArenaLevelInfo(this) {
            override val specialRoundEnemies: Array<Class<out Enemy?>> get() = arrayOf(TankEnemy::class.java, Zombie::class.java)
            override val boss: Boss get() = Clown()
            override val maxDestroyableBlocks: Int get() = 10
            override val nextLevel: Class<out Level>? get() = null
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(FastEnemy::class.java, TankEnemy::class.java, Eagle::class.java)
            override val worldId: Int get() = 2
            override val levelId: Int get() = 0
        }
}