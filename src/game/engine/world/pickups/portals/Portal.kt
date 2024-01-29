package game.engine.world.pickups.portals

import game.Bomberman
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.world.geo.Coordinates
import game.engine.world.pickups.powerups.PowerUp

abstract class Portal : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val isDisplayable: Boolean = false

    override fun doApply(entity: BomberEntity) {
        Bomberman.getMatch().toggleGameState()
    }
}