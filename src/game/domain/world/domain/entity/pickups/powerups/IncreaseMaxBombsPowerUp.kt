package game.domain.world.domain.entity.pickups.powerups

import game.JBomb
import game.data.data.DataInputOutput
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.events.game.UpdateMaxBombsEvent
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.domain.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.domain.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.localization.Localization
import game.utils.file_system.Paths.powerUpsFolder
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

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            UpdateMaxBombsEvent().invoke(player.state.maxBombs + 1, true)
        }

        override fun cancel(player: BomberEntity) {}

        override fun canPickUp(bomberEntity: BomberEntity): Boolean =
                DataInputOutput.getInstance().obtainedBombs < JBomb.match.currentLevel.info.maxBombs && super.canPickUp(bomberEntity)
    }

    override val state: PowerUpState = object : PowerUpState(entity = this) {
        override val duration: Int = 0
    }

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadAndSetImage(entity, "$powerUpsFolder/increase_max_bombs_powerup.png")
        }
    }

    override val tag: String
        get() = Localization.get(Localization.BOMBS_POWERUP)

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.IncreaseMaxBombsPowerUp)
}