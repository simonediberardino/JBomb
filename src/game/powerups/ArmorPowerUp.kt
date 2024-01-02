package game.powerups

import game.entity.EntityTypes
import game.entity.player.BomberEntity
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
    constructor(id: Long) : this(null) {
        this.id = id
    }

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/armor_up.png")

    override val duration: Int
        get() = DEFAULT_DURATION_SEC

    override fun doApply(entity: BomberEntity) {
        entity.isImmune = true
    }

    override fun cancel(entity: BomberEntity) {
        if (entity.isSpawned) entity.isImmune = false
    }

    override fun getType(): EntityTypes = EntityTypes.ArmorPowerUp
}