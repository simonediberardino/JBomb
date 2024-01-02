package game.powerups

import game.entity.EntityTypes
import game.entity.bomb.AbstractExplosion.Companion.MAX_EXPLOSION_LENGTH
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.events.game.ExplosionLengthPowerUpEvent
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class FirePowerUp
/**
 * Constructs an entity with the given coordinates.
 *
 * @param coordinates the coordinates of the entity
 */
(coordinates: Coordinates?) : PowerUp(coordinates) {
    constructor(id: Long) : this(null) {
        this.id = id
    }

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/fire_up.png")

    override val duration: Int
        get() {
            return 0
        }

    override fun doApply(entity: BomberEntity) {
        ExplosionLengthPowerUpEvent().invoke(entity)
    }

    override fun cancel(entity: BomberEntity) {}

    override fun canPickUp(entity: BomberEntity): Boolean = entity.currExplosionLength <= MAX_EXPLOSION_LENGTH

    override fun getType(): EntityTypes = EntityTypes.FirePowerUp
}