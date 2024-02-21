package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class TransparentBombsPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/transparent_bomb_powerup.png")

    override fun doApply(entity: BomberEntity) {
        entity.forceSetBombsNotSolid(true)
        entity.setBombsSolid(false)
    }

    override fun cancel(entity: BomberEntity) {
        entity.forceSetBombsNotSolid(false)
        entity.setBombsSolid(true)
    }

    override val type: EntityTypes
        get() = EntityTypes.TransparentBombsPowerUp
}