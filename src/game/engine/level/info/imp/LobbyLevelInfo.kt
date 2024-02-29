package game.engine.level.info.imp

import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.info.model.DefaultLevelInfo
import game.engine.level.levels.Level

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