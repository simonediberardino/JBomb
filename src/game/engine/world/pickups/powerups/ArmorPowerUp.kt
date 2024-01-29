package game.engine.world.pickups.powerups

import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.world.geo.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class ArmorPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/armor_up.png")

    override val duration: Int
        get() = DEFAULT_DURATION_SEC

    override fun doApply(entity: BomberEntity) {
        entity.isImmune = true
    }

    override fun cancel(entity: BomberEntity) {
        if (entity.isSpawned) entity.isImmune = false
    }

    override val type: EntityTypes
        get() = EntityTypes.ArmorPowerUp
}