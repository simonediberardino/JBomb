package game.domain.world.domain.entity.actors.impl.enemies.npcs.flying_enemy

import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.properties.FlyingEnemyState

/**
 * Enemy that can fly over destroyable blocks;
 */
abstract class FlyingEnemy : AiEnemy {
    constructor() : super()
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor(id: Long) : super(id)

    override val state: EnemyEntityState = FlyingEnemyState(entity = this)

    internal object DEFAULT {
        val OBSTACLES: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.OBSTACLES.apply {
            remove(DestroyableBlock::class.java)
        }
    }
}