package game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.properties

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.pickups.powerups.PowerUp

class DestroyableBlockState(
        entity: Entity,
        var powerUpClass: Class<out PowerUp>? = null
) : BlockEntityState(
        entity = entity,
        interactionEntities = mutableSetOf(AbstractExplosion::class.java)
)