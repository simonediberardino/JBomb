package game.powerups

import game.entity.blocks.DestroyableBlock
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class TransparentDestroyableBlocksPowerUp(coords: Coordinates?) : PowerUp(coords) {
    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/blocks_up.gif")

    override val duration: Int = 0

    override fun doApply(entity: BomberEntity) {
        entity.addWhiteListObstacle(DestroyableBlock::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.removeWhiteListObstacle(DestroyableBlock::class.java)
    }
}