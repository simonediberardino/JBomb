package game.engine.world.domain.entity.actors.abstracts.moving_entity

import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.abstracts.moving_entity.logic.IMovingEntityLogic
import game.engine.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityProperties
import game.engine.world.domain.entity.geo.Coordinates

abstract class MovingEntity : EntityInteractable {
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor(id: Long) : super(id)
    constructor() : super()

    abstract override val logic: IMovingEntityLogic
    abstract override val properties: MovingEntityProperties
}
