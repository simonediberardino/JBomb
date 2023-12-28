package game.powerups

import game.entity.bomb.Bomb
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class RemoteControl
/**
 * Constructs a PowerUp entity with the specified coordinates.
 *
 * @param coordinates the coordinates of the PowerUp entity
 */
(coordinates: Coordinates?) : PowerUp(coordinates) {
    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/remote_up.png")

    override val duration: Int = 30

    override fun doApply(entity: BomberEntity) {
        entity.addClassInteractWithMouseClick(Bomb::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.removeClassInteractWithMouseClick(Bomb::class.java)
    }

}