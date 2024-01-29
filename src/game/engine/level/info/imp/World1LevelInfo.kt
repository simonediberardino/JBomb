package game.engine.level.info.imp

import game.engine.world.entity.impl.enemies.boss.Boss
import game.engine.level.levels.Level
import game.engine.level.info.model.DefaultStoryLevelInfo

abstract class World1LevelInfo(level: Level): DefaultStoryLevelInfo(level) {
    override val worldId: Int get() = 1

    override val boss: Boss? get() = null
    override val maxDestroyableBlocks: Int get() = 10
}