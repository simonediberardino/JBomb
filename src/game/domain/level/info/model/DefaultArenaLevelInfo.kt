package game.domain.level.info.model

import game.domain.level.levels.ArenaLevel
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.items.BombItem
import game.domain.world.domain.entity.items.UsableItem
import game.localization.Localization
import kotlin.math.max
import kotlin.math.min

abstract class DefaultArenaLevelInfo(level: ArenaLevel): DefaultLevelInfo(level) {
    override val isArenaLevel: Boolean get() = true

    override val diedMessage: String get() = Localization.get(Localization.ARENA_DIED).replace("%rounds%", ArenaLevel.CURR_ROUND.toString())
    override val bossMaxHealth: Int get() = super.bossMaxHealth / 2

    override val startEnemiesCount: Int get() {
            return min(ArenaLevel.MIN_ENEMIES_COUNT + (level as ArenaLevel).currentRound.get(), ArenaLevel.MAX_ENEMIES_COUNT)
        }

    abstract val specialRoundEnemies: Array<Class<out Enemy>>

    override val defaultWeapon: UsableItem = BombItem()
}