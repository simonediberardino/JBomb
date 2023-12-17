package game.powerups

import game.Bomberman
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.items.PistolItem
import game.utils.Paths.itemsPath
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage
class PistolPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {

    override fun getImage(): BufferedImage =
            loadAndSetImage("$itemsPath/pistol.png")

    override val duration: Int
        get() = 30

    override fun doApply(entity: BomberEntity) {
        Bomberman.getMatch().give(entity, PistolItem())
    }

    override fun cancel(entity: BomberEntity) {
        Bomberman.getMatch().removeItem(entity)
    }

    override val isDisplayable: Boolean
        get() = false
}
