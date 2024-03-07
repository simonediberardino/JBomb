package game.engine.world.domain.entity.pickups.powerups

import game.Bomberman
import game.storage.data.DataInputOutput
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.events.game.UpdateMaxBombsEvent
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker.Default

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
            UpdateMaxBombsEvent().invoke(player.state.maxBombs + 1)
        }

        override fun cancel(player: BomberEntity) {}

        override fun canPickUp(bomberEntity: BomberEntity): Boolean =
                DataInputOutput.getInstance().obtainedBombs < Bomberman.getMatch().currentLevel!!.info.maxBombs
    }

    override val state: PowerUpState = object : PowerUpState(entity = this) {
        override val duration: Int = 0
    }

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadAndSetImage(entity, "$powerUpsFolder/increase_max_bombs_powerup.png")
        }
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.IncreaseMaxBombsPowerUp)
}