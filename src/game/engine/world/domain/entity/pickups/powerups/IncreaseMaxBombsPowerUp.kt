package game.engine.world.domain.entity.pickups.powerups

import game.Bomberman
import game.storage.data.DataInputOutput
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.events.game.UpdateMaxBombsEvent
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class IncreaseMaxBombsPowerUp
/**
 * Constructs a PowerUp entity with the specified coordinates.
 *
 * @param coordinates the coordinates of the PowerUp entity
 */
 : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/increase_max_bombs_powerup.png")

    override val duration: Int = 0

    override fun doApply(entity: BomberEntity) {
        UpdateMaxBombsEvent().invoke(entity.currentBombs + 1)
    }

    override fun cancel(entity: BomberEntity) {
        // No need to implement anything here
    }

    override fun canPickUp(entity: BomberEntity): Boolean =
            DataInputOutput.getInstance().obtainedBombs < Bomberman.getMatch().currentLevel!!.info.maxBombs

    override val type: EntityTypes
        get() = EntityTypes.IncreaseMaxBombsPowerUp
}