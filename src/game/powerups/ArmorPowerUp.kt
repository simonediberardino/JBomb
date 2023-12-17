package game.powerups

import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class ArmorPowerUp
/**
 * Constructs an entity with the given coordinates.
 *
 * @param coordinates the coordinates of the entity
 */
(coordinates: Coordinates?) : PowerUp(coordinates) {
    override fun getImage(): BufferedImage {
        return loadAndSetImage("$powerUpsFolder/armor_up.png")
    }

    override val duration: Int
        get() {
            return DEFAULT_DURATION_SEC
        }

    override fun doApply(entity: BomberEntity) {
        entity.isImmune = true
    }

    override fun cancel(entity: BomberEntity) {
        if (entity.isSpawned) entity.isImmune = false
    }
}