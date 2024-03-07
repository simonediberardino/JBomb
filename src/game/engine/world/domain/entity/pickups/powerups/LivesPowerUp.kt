package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.storage.data.DataInputOutput
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class LivesPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage = loadAndSetImage(entity, "$powerUpsFolder/lives_up.png")
    }

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            DataInputOutput.getInstance().increaseLives()
        }

        override fun cancel(player: BomberEntity) {}

        override fun canPickUp(bomberEntity: BomberEntity): Boolean = true
    }

    override val state: PowerUpState = object : PowerUpState(entity = this) {
        override val duration: Int = 0
        override val isDisplayable: Boolean = false
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.LivesPowerUp)
}
