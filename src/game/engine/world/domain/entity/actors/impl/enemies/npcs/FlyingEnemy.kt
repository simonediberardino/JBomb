package game.engine.world.domain.entity.actors.impl.enemies.npcs

import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.enemies.ai_enemy.AiEnemy

/**
 * Enemy that can fly over destroyable blocks;
 */
abstract class FlyingEnemy : AiEnemy {
    constructor() : super()
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor(id: Long) : super(id)

    override fun getObstacles(): Set<Class<out Entity>> = HashSet(super.getObstacles()).apply {
        remove(DestroyableBlock::class.java)
    }
}