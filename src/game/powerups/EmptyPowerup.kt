package game.powerups

import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class EmptyPowerup
/**
 * Constructs a PowerUp entity with the specified coordinates.
 *
 * @param coordinates the coordinates of the PowerUp entity
 */
(coordinates: Coordinates?) : PowerUp(coordinates) {
    override fun getImage(): BufferedImage {
        return loadAndSetImage("$powerUpsFolder/no_powerup.png")
    }

    override val duration: Int
        get() {
            return 0
        }

    override fun doApply(entity: BomberEntity) {}
    override fun cancel(entity: BomberEntity) {}
}