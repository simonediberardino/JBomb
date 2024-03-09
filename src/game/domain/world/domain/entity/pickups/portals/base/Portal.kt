package game.domain.world.domain.entity.pickups.portals.base

import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.portals.base.logic.PortalLogic
import game.domain.world.domain.entity.pickups.portals.base.state.PortalState
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp

abstract class Portal : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val state: PortalState = PortalState(entity = this)
    override val image: EntityImageModel = EntityImageModel(entity = this)
    abstract override val logic: PortalLogic
}