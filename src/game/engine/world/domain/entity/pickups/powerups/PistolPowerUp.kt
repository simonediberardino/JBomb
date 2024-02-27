package game.engine.world.domain.entity.pickups.powerups

import game.Bomberman
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.items.PistolItem
import game.utils.Paths.itemsPath
import java.awt.image.BufferedImage

class PistolPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage =
            loadAndSetImage("$itemsPath/pistol.png")

    override val duration: Int
        get() = 30

    override fun doApply(entity: BomberEntity) {
        Bomberman.getMatch().give(entity, PistolItem(),true)
    }

    override fun cancel(entity: BomberEntity) {
        Bomberman.getMatch().removeItem(entity)
    }

    override val isDisplayable: Boolean
        get() = false

    override fun canPickUp(entity: BomberEntity): Boolean = true

    override val type: EntityTypes
        get() = EntityTypes.PistolPowerUp
}
