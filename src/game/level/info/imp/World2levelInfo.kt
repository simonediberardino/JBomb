package game.level.info.imp

import game.entity.enemies.boss.Boss
import game.level.Level
import game.level.info.model.DefaultStoryLevelInfo

abstract class World2levelInfo(level: Level): DefaultStoryLevelInfo(level) {
    override val worldId: Int get() = 2
    override val maxDestroyableBlocks: Int get() = 15
    override val boss: Boss? get() = null
}