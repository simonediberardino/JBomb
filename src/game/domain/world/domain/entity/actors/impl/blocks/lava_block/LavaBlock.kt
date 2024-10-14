package game.domain.world.domain.entity.actors.impl.blocks.lava_block

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.PeriodicGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityProperties
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.utils.file_system.Paths.blocksFolder
import java.awt.image.BufferedImage

class LavaBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val properties: EntityProperties = BlockEntityProperties(type = EntityTypes.LavaBlock)

    override val logic: IBlockEntityLogic = object : BlockEntityLogic(this) {

        override fun onCollision(e: Entity) {
            super.onCollision(e)
            if (e is Character) {
                e.logic.onAttackReceived(100, e)
            }
        }
    }

    override val image: EntityImageModel = EntityImageModel(
        entity = this,
        entitiesAssetsPath = "$blocksFolder/lava/lavablock%format%.png"
    )

    override val graphicsBehavior: IEntityGraphicsBehavior = object: PeriodicGraphicsBehavior() {
        override val imagesCount: Int
            get() = 3
        override val allowUiState: Boolean
            get() = false
    }
}