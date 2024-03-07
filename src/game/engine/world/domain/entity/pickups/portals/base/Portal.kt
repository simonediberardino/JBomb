package game.engine.world.domain.entity.pickups.portals.base

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.portals.base.logic.PortalLogic
import game.engine.world.domain.entity.pickups.portals.base.state.PortalState
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState

abstract class Portal : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val state: PortalState = PortalState(entity = this)
    override val image: EntityImageModel = EntityImageModel(entity = this)
    abstract override val logic: PortalLogic
}