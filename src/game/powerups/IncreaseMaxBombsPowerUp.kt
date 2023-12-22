package game.powerups

import game.Bomberman
import game.data.DataInputOutput
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.events.game.UpdateMaxBombsEvent
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class IncreaseMaxBombsPowerUp
/**
 * Constructs a PowerUp entity with the specified coordinates.
 *
 * @param coordinates the coordinates of the PowerUp entity
 */
(coordinates: Coordinates?) : PowerUp(coordinates) {
    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/increase_max_bombs_powerup.png")

    override val duration: Int = 0

    override fun doApply(entity: BomberEntity) {
        UpdateMaxBombsEvent().invoke(entity.currentBombs + 1)
    }

    override fun cancel(entity: BomberEntity) {
        // No need to implement anything here
    }

    override fun canPickUp(entity: BomberEntity): Boolean =
            DataInputOutput.getInstance().obtainedBombs < Bomberman.getMatch().currentLevel!!.info.maxBombs
}