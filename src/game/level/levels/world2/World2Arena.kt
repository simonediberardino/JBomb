package game.level.levels.world2

import game.entity.enemies.boss.Boss
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.npcs.*
import game.entity.models.Enemy
import game.level.levels.ArenaLevel
import game.level.levels.Level
import game.level.info.model.DefaultArenaLevelInfo
import game.level.info.model.LevelInfo

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