package game.domain.level.info.model

import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.ArenaLevel
import game.localization.Localization

abstract class DefaultArenaLevelInfo(level: ArenaLevel): DefaultLevelInfo(level) {
    override val isArenaLevel: Boolean get() = true

    override val diedMessage: String get() = Localization.get(Localization.ARENA_DIED).replace("%rounds%", ArenaLevel.CURR_ROUND.toString())
    override val bossMaxHealth: Int get() = super.bossMaxHealth / 4

    override val startEnemiesCount: Int get() {
            return ArenaLevel.MIN_ENEMIES_COUNT + (level as ArenaLevel).currentRound.get()
        }

    abstract val specialRoundEnemies: Array<Class<out Enemy>>
}