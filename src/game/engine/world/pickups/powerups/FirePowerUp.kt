package game.engine.world.pickups.powerups

import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.bomb.AbstractExplosion.Companion.MAX_EXPLOSION_LENGTH
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.world.geo.Coordinates
import game.engine.events.game.ExplosionLengthPowerUpEvent
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class FirePowerUp
/**
 * Constructs an entity with the given coordinates.
 *
 * @param coordinates the coordinates of the entity
 */
 : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

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

    override val type: EntityTypes
        get() = EntityTypes.FirePowerUp
}