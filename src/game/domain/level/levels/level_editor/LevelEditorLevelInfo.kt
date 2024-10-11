package game.domain.level.levels.level_editor

import game.domain.level.info.model.DefaultLevelInfo
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.localization.Localization

class LevelEditorLevelInfo(level: Level): DefaultLevelInfo(level) {
    override val boss: Boss?
        get() = null
    override val startEnemiesCount: Int
        get() = 0
    override val maxDestroyableBlocks: Int
        get() = 0
    override val isArenaLevel: Boolean
        get() = false
    override val diedMessage: String
        get() = ""
    override val nextLevel: Class<out Level?>?
        get() = null
    override val availableEnemies: Array<Class<out Enemy>>
        get() = emptyArray()
    override val worldId: Int
        get() = -1
    override val levelId: Int
        get() = 0
}