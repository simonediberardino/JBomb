package game.domain.world.domain.entity.actors.impl.blocks.misc_blocks

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityProperties
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.utils.file_system.Paths.blocksFolder
import java.awt.image.BufferedImage

class Block1: HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val properties: EntityProperties = BlockEntityProperties(type = EntityTypes.Block1)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadAndSetImage(
                entity = entity,
                imagePath = "$blocksFolder/block1/block1.png"
            )
        }
    }
}