package game.powerups

import game.entity.EntityTypes
import game.entity.blocks.MovableBlock
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class BlockMoverPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    init {
        incompatiblePowerUps += HammerPowerUp::class.java
    }

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/hand.png")

    override val duration: Int
        get() = 30

    override fun doApply(entity: BomberEntity) = entity.addClassInteractWithMouseDrag(MovableBlock::class.java)

    override fun cancel(entity: BomberEntity) = entity.removeClassInteractWithDrag(MovableBlock::class.java)

    override val type: EntityTypes
        get() = EntityTypes.BlockMoverPowerUp
}
