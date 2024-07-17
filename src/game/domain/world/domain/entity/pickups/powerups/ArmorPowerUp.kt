package game.domain.world.domain.entity.pickups.powerups

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.domain.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.localization.Localization
import game.utils.dev.Log
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class ArmorPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            player.state.isImmune = true
            player.logic.onImmuneChangedState()
        }

        override fun cancel(player: BomberEntity) {
            if (player.state.isSpawned) {
                player.state.isImmune = false
                player.logic.onImmuneChangedState()
            }
        }
    }

    override val graphicsBehavior : IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? = loadAndSetImage(entity, "$powerUpsFolder/armor_up.png")
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.ArmorPowerUp)
    override val tag: String
        get() = Localization.get(Localization.ARMOR_POWER_UP)
}