package game.level.world1

import game.entity.enemies.boss.Boss
import game.entity.enemies.boss.ghost.GhostBoss
import game.entity.enemies.npcs.Helicopter
import game.entity.enemies.npcs.TankEnemy
import game.entity.enemies.npcs.YellowBall
import game.entity.enemies.npcs.Zombie
import game.entity.models.Enemy
import game.level.ArenaLevel
import game.level.Level
import game.level.info.model.DefaultArenaLevelInfo
import game.level.info.model.LevelInfo

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