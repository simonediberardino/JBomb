package game.engine.world.pickups.powerups

import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.blocks.DestroyableBlock
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.world.geo.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class HammerPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    init {
        incompatiblePowerUps.add(BlockMoverPowerUp::class.java)
    }

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/hammer.png")

    override val duration: Int
        get() = 30

    override fun doApply(entity: BomberEntity) {
        entity.addClassInteractWithMouseClick(DestroyableBlock::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.removeClassInteractWithMouseClick(DestroyableBlock::class.java)
    }

    override val type: EntityTypes
        get() =  EntityTypes.HammerPowerUp
}