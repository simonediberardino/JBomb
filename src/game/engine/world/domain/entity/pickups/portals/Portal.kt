package game.engine.world.domain.entity.pickups.portals

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.powerups.PowerUp

abstract class Portal : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val isDisplayable: Boolean = false

    override fun doApply(entity: BomberEntity) {
        Bomberman.getMatch().toggleGameState()
    }
}