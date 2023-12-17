package game.powerups

import game.entity.blocks.MovableBlock
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class BlockMoverPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {

    init {
        incompatiblePowerUps += Hammer::class.java
    }

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/hand.png")

    override val duration: Int
        get() = 30

    override fun doApply(entity: BomberEntity) {
        entity.addClassInteractWithMouseDrag(MovableBlock::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.removeClassInteractWithDrag(MovableBlock::class.java)
    }
}
