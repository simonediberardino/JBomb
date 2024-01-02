package game.powerups

import game.entity.EntityTypes
import game.entity.blocks.DestroyableBlock
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class HammerPowerUp(coordinates: Coordinates?) : PowerUp(coordinates) {
    constructor(id: Long) : this(null) {
        this.id = id
    }

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

    override fun getType(): EntityTypes = EntityTypes.HammerPowerUp
}