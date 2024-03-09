package game.domain.world.domain.entity.pickups.powerups

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.input.ControllerManager
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.domain.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class SpeedPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage = loadAndSetImage(entity, "$powerUpsFolder/speed_up.png")
    }

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            ControllerManager.decreaseCommandDelay()
        }

        override fun cancel(player: BomberEntity) {
            ControllerManager.setDefaultCommandDelay()
        }

        override fun canPickUp(bomberEntity: BomberEntity): Boolean = true
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.SpeedPowerUp)
}