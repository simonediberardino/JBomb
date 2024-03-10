package game.domain.world.domain.entity.actors.impl.blocks.base_block

import game.domain.world.domain.entity.geo.Coordinates
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityInfo
import game.domain.world.domain.entity.actors.abstracts.base.EntityState
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState

abstract class Block : Entity {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: IBlockEntityLogic = BlockEntityLogic(entity = this)
    override val state: BlockEntityState = BlockEntityState(entity = this)
    
    override val image: EntityImageModel = EntityImageModel(entity = this)

    companion object {
        val SIZE = PitchPanel.GRID_SIZE
    }
}