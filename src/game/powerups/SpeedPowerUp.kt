package game.powerups

import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.hardwareinput.ControllerManager
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class SpeedPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {
    override fun getBasePath(): String? = null

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/speed_up.png")

    override fun doApply(entity: BomberEntity) {
        ControllerManager.decreaseCommandDelay()
    }

    override fun cancel(entity: BomberEntity) {
        ControllerManager.setDefaultCommandDelay()
    }
}