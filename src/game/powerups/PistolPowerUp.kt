package game.powerups

import game.Bomberman
import game.entity.EntityTypes
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.items.PistolItem
import game.utils.Paths.itemsPath
import java.awt.image.BufferedImage

class PistolPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {
    constructor(id: Long) : this(null) {
        this.id = id
    }

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

    override fun getType(): EntityTypes = EntityTypes.PistolPowerUp
}
