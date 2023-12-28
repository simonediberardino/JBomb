package game.powerups

import game.data.DataInputOutput
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage
class LivesPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {

    override fun getImage(): BufferedImage =
            loadAndSetImage("$powerUpsFolder/lives_up.png")

    override val duration: Int
        get() = 0

    override fun doApply(entity: BomberEntity) {
        DataInputOutput.getInstance().increaseLives()
    }

    override fun cancel(entity: BomberEntity) {
        // No need to implement anything here
    }

    override val isDisplayable: Boolean
        get() = false

    override fun canPickUp(entity: BomberEntity): Boolean = true
}
