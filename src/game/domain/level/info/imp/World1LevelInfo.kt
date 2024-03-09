package game.domain.level.info.imp

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.level.levels.Level
import game.domain.level.info.model.DefaultStoryLevelInfo

abstract class World1LevelInfo(level: Level): DefaultStoryLevelInfo(level) {
    override val worldId: Int get() = 1

    override val boss: Boss? get() = null
    override val maxDestroyableBlocks: Int get() = 10
}