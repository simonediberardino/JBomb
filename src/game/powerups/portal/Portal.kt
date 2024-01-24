package game.powerups.portal

import game.Bomberman
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.powerups.PowerUp

abstract class Portal : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val isDisplayable: Boolean = false

    override fun doApply(entity: BomberEntity) {
        Bomberman.getMatch().toggleGameState()
    }
}