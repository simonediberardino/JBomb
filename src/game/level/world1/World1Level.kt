package game.level.world1

import game.entity.enemies.boss.Boss
import game.level.StoryLevel

abstract class World1Level : StoryLevel() {
    override val worldId: Int
        get() {
            return 1
        }

    override val boss: Boss?
        get() {
            return null
        }

    override val maxDestroyableBlocks: Int
        get() {
            return 10
        }
}