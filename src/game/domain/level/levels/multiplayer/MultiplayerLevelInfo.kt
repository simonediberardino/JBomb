package game.domain.level.levels.multiplayer

import game.data.data.DataInputOutput
import game.domain.level.info.model.DefaultLevelInfo
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.items.BombItem
import game.domain.world.domain.entity.items.PistolItem
import game.domain.world.domain.entity.items.UsableItem
import game.localization.Localization

open class MultiplayerLevelInfo(private val mapId: Int, level: Level): DefaultLevelInfo(level) {
    override val boss: Boss?
        get() = null
    override val startEnemiesCount: Int
        get() = 0
    override val maxDestroyableBlocks: Int
        get() = 10
    override val isArenaLevel: Boolean
        get() = false
    override val diedMessage: String
        get() = Localization.get(Localization.YOU_DIED_BASE)
    override val nextLevel: Class<out Level?>?
        get() = null
    override val availableEnemies: Array<Class<out Enemy>>
        get() = emptyArray()
    override val worldId: Int
        get() = -1
    override val levelId: Int
        get() = mapId

    override val defaultWeapon: UsableItem
        get() = PistolItem(Integer.MAX_VALUE)

    override val networkName: String = "Multiplayer: ${level.toString()}"
    override val isTimeEnabled: Boolean = true
    override val timeLimitMinutes: Int = 1
}