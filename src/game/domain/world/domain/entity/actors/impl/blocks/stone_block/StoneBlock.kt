package game.domain.world.domain.entity.actors.impl.blocks.stone_block

import game.Bomberman
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityProperties
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import java.awt.image.BufferedImage

class StoneBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val properties: EntityProperties = BlockEntityProperties(type = EntityTypes.StoneBlock)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadAndSetImage(
                    entity = entity,
                    imagePath = Bomberman.match.currentLevel!!.info.stoneBlockImagePath
            )
        }
    }
}
