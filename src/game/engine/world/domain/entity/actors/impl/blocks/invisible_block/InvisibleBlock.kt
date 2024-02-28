package game.engine.world.domain.entity.actors.impl.blocks.invisible_block

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityProperties
import game.engine.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.types.EntityTypes
import java.awt.image.BufferedImage

class InvisibleBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val properties: EntityProperties = BlockEntityProperties(
            type = EntityTypes.InvisibleBlock
    )

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return null
        }
    }
}
