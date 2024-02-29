package game.engine.level.info.imp

import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.level.levels.Level
import game.engine.level.info.model.DefaultStoryLevelInfo

abstract class World2levelInfo(level: Level): DefaultStoryLevelInfo(level) {
    override val worldId: Int get() = 2
    override val maxDestroyableBlocks: Int get() = 15
    override val boss: Boss? get() = null
}