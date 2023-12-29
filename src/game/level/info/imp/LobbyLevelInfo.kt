package game.level.info.imp

import game.entity.enemies.boss.Boss
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.level.info.model.DefaultLevelInfo
import game.level.levels.Level
import java.awt.Dimension

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