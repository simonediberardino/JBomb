package game.domain.world.domain.entity.actors.impl.bomber_entity.player.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityState
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction
import game.input.Command
import java.util.concurrent.atomic.AtomicReference

class PlayerState(
        entity: BomberEntity,
) : BomberEntityState(entity = entity
) {
    val commandQueue: MutableSet<Command> = mutableSetOf()
}