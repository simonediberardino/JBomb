package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class RemoteControlPowerUp
/**
 * Constructs a PowerUp entity with the specified coordinates.
 *
 * @param coordinates the coordinates of the PowerUp entity
 */
 : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/remote_up.png")

    override val duration: Int = 30

    override fun doApply(entity: BomberEntity) {
        entity.addClassInteractWithMouseClick(Bomb::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.removeClassInteractWithMouseClick(Bomb::class.java)
    }

    override val type: EntityTypes
        get() =  EntityTypes.RemoteControlPowerUp
}