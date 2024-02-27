package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class EmptyPowerup
/**
 * Constructs a PowerUp entity with the specified coordinates.
 *
 * @param coordinates the coordinates of the PowerUp entity
 */
 : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/no_powerup.png")

    override val duration: Int
        get() = 0

    override fun doApply(entity: BomberEntity) {}
    override fun cancel(entity: BomberEntity) {}

    override val type: EntityTypes
        get() = EntityTypes.EmptyPowerup

}