package game.engine.world.domain.entity.actors.impl.enemies.boss.clown.hat.properties

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityState

class HatEntityState(
        override var entity: Entity,
        interactionEntities: MutableSet<Class<out Entity>> = Hat.DEFAULT.INTERACTION_ENTITIES,
        size: Int = Hat.DEFAULT.SIZE,
        maxHp: Int = Hat.DEFAULT.MAX_HP
) : OrbEntityState(
        entity = entity,
        interactionEntities = interactionEntities,
        size = size,
        maxHp = maxHp
)