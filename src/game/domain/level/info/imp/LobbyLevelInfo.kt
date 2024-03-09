package game.domain.level.info.imp

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.info.model.DefaultLevelInfo
import game.domain.level.levels.Level

abstract class LobbyLevelInfo(level: Level) : DefaultLevelInfo(level) {
    override val startEnemiesCount: Int get() = 0
    override val maxBombs: Int get() = 0
    override val maxDestroyableBlocks: Int get() = 0
    override val isArenaLevel: Boolean get() = false
    override val diedMessage: String? get() = null
    override val nextLevel: Class<out Level?>? get() = null
    override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf()
    override val boss: Boss? get() = null
}