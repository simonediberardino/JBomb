package game.level.info.imp

import game.entity.enemies.boss.Boss
import game.level.levels.Level
import game.level.info.model.DefaultStoryLevelInfo

abstract class World1LevelInfo(level: Level): DefaultStoryLevelInfo(level) {
    override val worldId: Int get() = 1

    override val boss: Boss? get() = null
    override val maxDestroyableBlocks: Int get() = 10
}