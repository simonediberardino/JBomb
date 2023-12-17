package game.powerups

import game.entity.blocks.DestroyableBlock
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class Hammer(coordinates: Coordinates?) : PowerUp(coordinates) {
    /**
     * Constructs a PowerUp entity with the specified coordinates.
     *
     * @param coordinates the coordinates of the PowerUp entity
     */
    init {
        incompatiblePowerUps.add(BlockMoverPowerUp::class.java)
    }

    override fun getImage(): BufferedImage {
        return loadAndSetImage("$powerUpsFolder/hammer.png")
    }

    override val duration: Int
        get() = 30

    override fun doApply(entity: BomberEntity) {
        entity!!.listClassInteractWithMouseClick.add(DestroyableBlock::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity!!.listClassInteractWithMouseClick.remove(DestroyableBlock::class.java)
    }
}