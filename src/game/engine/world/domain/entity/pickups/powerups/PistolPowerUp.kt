package game.engine.world.domain.entity.pickups.powerups

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.items.PistolItem
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.storage.data.DataInputOutput
import game.utils.file_system.Paths
import game.utils.file_system.Paths.itemsPath
import java.awt.image.BufferedImage

class PistolPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage = loadAndSetImage(entity, "$itemsPath/pistol.png")
    }

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            Bomberman.getMatch().give(player, PistolItem(), true)
        }

        override fun cancel(player: BomberEntity) {
            Bomberman.getMatch().removeItem(player)
        }

        override fun canPickUp(bomberEntity: BomberEntity): Boolean = true
    }


    override val properties: EntityProperties = EntityProperties(type = EntityTypes.PistolPowerUp)
}
