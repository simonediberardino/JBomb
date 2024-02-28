package game.engine.world.domain.entity.actors.impl.blocks.base_block

import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.base.EntityState
import game.engine.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.engine.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic
import game.engine.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState

abstract class Block : Entity {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: IBlockEntityLogic = BlockEntityLogic(entity = this)
    override val state: EntityState = BlockEntityState(entity = this)
    override val info: EntityInfo = EntityInfo()
    override val image: EntityImageModel = EntityImageModel(entity = this)

    companion object {
        val SIZE = PitchPanel.GRID_SIZE
    }
}