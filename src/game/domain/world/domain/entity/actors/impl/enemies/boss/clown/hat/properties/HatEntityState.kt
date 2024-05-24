package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityState

class HatEntityState(
        entity: Entity,
        interactionEntities: MutableSet<Class<out Entity>> = Hat.DEFAULT.INTERACTION_ENTITIES,
        size: Int = Hat.DEFAULT.SIZE,
        maxHp: Int = Hat.DEFAULT.MAX_HP,
        obstacles: MutableSet<Class<out Entity>> = Hat.DEFAULT.OBSTACLES
) : OrbEntityState(
        entity = entity,
        interactionEntities = interactionEntities,
        size = size,
        maxHp = maxHp,
        obstacles = obstacles
)