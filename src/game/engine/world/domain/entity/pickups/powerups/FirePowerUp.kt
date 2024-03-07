package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion.Companion.MAX_EXPLOSION_LENGTH
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.events.game.ExplosionLengthPowerUpEvent
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class FirePowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage = loadAndSetImage(entity, "$powerUpsFolder/fire_up.png")
    }

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            ExplosionLengthPowerUpEvent().invoke(player)
        }

        override fun cancel(player: BomberEntity) {}

        override fun canPickUp(bomberEntity: BomberEntity): Boolean = bomberEntity.state.currExplosionLength <= MAX_EXPLOSION_LENGTH
    }

    override val state: PowerUpState = object : PowerUpState(entity = this) {
        override val duration: Int = 0
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.FirePowerUp)
}
