package game.powerups

import game.entity.EntityTypes
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.hardwareinput.ControllerManager
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class SpeedPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {
    constructor(id: Long) : this(null) {
        this.id = id
    }

    override fun getEntitiesAssetsPath(): String? = null

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/speed_up.png")

    override fun doApply(entity: BomberEntity) {
        ControllerManager.decreaseCommandDelay()
    }

    override fun cancel(entity: BomberEntity) {
        ControllerManager.setDefaultCommandDelay()
    }

    override fun getType(): EntityTypes = EntityTypes.SpeedPowerUp
}