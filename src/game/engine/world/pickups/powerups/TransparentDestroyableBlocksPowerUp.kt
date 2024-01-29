package game.engine.world.pickups.powerups

import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.blocks.DestroyableBlock
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.world.geo.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class TransparentDestroyableBlocksPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/blocks_up.gif")

    override val duration: Int = 0

    override fun doApply(entity: BomberEntity) {
        entity.addWhiteListObstacle(DestroyableBlock::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.removeWhiteListObstacle(DestroyableBlock::class.java)
    }

    override val type: EntityTypes
        get() = EntityTypes.TransparentDestroyableBlocksPowerUp
}