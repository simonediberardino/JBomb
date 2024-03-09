package game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp

class DestroyableBlockState(
        entity: Entity,
        var powerUpClass: Class<out PowerUp>? = null
) : BlockEntityState(
        entity = entity,
        interactionEntities = mutableSetOf(AbstractExplosion::class.java)
)