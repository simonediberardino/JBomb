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

class World1Arena : ArenaLevel() {
    override val boss: Boss
        get() {
            return GhostBoss()
        }

    override val maxDestroyableBlocks: Int
        get() {
            return 10
        }

    override val nextLevel: Class<out Level?>?
        get() {
            return null
        }

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf(
                    YellowBall::class.java,
                    Helicopter::class.java,
                    Zombie::class.java
            )
        }

    override val worldId: Int
        get() {
            return 1
        }

    override val levelId: Int
        get() {
            return 0
        }

    override val specialRoundEnemies: Array<Class<out Enemy?>>
        get() {
            return arrayOf(
                    TankEnemy::class.java,
                    Zombie::class.java
            )
        }
}