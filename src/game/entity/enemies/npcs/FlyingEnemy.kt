package game.entity.enemies.npcs

import game.entity.blocks.DestroyableBlock
import game.entity.models.Coordinates
import game.entity.models.Entity

/**
 * Enemy that can fly over destroyable blocks;
 */
abstract class FlyingEnemy : IntelligentEnemy {
    constructor() : super()
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor(id: Long) : super(id)

    override fun getObstacles(): Set<Class<out Entity>> = HashSet(super.getObstacles()).apply {
        remove(DestroyableBlock::class.java)
    }
}