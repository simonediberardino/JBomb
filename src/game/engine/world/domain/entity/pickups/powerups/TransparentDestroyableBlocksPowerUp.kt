package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class TransparentDestroyableBlocksPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/blocks_up.gif")

    override val duration: Int = 0

    override fun doApply(entity: BomberEntity) {
        entity.state.whitelistObstacles.add(DestroyableBlock::class.java)
    }

    override fun cancel(entity: BomberEntity) {
        entity.state.whitelistObstacles.remove(DestroyableBlock::class.java)
    }

    override val type: EntityTypes
        get() = EntityTypes.TransparentDestroyableBlocksPowerUp
}