package game.powerups.portal

import game.Bomberman
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.powerups.PowerUp

abstract class Portal(coordinates: Coordinates?) : PowerUp(coordinates) {
    override val isDisplayable: Boolean
        get() {
            return false
        }

    override fun doApply(entity: BomberEntity) {
        Bomberman.getMatch().toggleGameState()
    }
}