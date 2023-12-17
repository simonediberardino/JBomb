package game.level.world2

import game.entity.enemies.boss.Boss
import game.level.StoryLevel

abstract class World2Level : StoryLevel() {
    override val worldId: Int
        get() {
            return 2
        }

    override val maxDestroyableBlocks: Int
        get() {
            return 15
        }

    override val boss: Boss?
        get() {
            return null
        }
}