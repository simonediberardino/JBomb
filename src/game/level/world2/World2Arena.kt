package game.level.world2

import game.entity.enemies.boss.Boss
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.npcs.Eagle
import game.entity.enemies.npcs.FastEnemy
import game.entity.enemies.npcs.TankEnemy
import game.entity.enemies.npcs.Zombie
import game.entity.models.Enemy
import game.level.ArenaLevel
import game.level.Level

class World2Arena : ArenaLevel() {
    override val specialRoundEnemies: Array<Class<out Enemy?>>
        get() {
            return arrayOf(
                    TankEnemy::class.java,
                    Zombie::class.java
            )
        }

    override val boss: Boss
        get() {
            return Clown()
        }

    override val maxDestroyableBlocks: Int
        get() {
            return 10
        }

    override val nextLevel: Class<out Level>?
        get() {
            return null
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    FastEnemy::class.java,
                    TankEnemy::class.java,
                    Eagle::class.java
            )
        }

    override val worldId: Int
        get() {
            return 2
        }

    override val levelId: Int
        get() {
            return 0
        }
}