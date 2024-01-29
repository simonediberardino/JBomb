package game.engine.world.entity.impl.enemies.npcs

import game.engine.world.entity.impl.blocks.DestroyableBlock
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity

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