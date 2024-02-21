package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.hardwareinput.ControllerManager
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class SpeedPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val entitiesAssetsPath: String?
        get() = null

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/speed_up.png")

    override fun doApply(entity: BomberEntity) {
        ControllerManager.decreaseCommandDelay()
    }

    override fun cancel(entity: BomberEntity) {
        ControllerManager.setDefaultCommandDelay()
    }

    override val type: EntityTypes
        get() = EntityTypes.SpeedPowerUp
}