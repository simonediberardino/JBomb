package game.engine.world.domain.entity.actors.impl.blocks.base_block

import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityState
import game.engine.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic
import game.engine.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState

abstract class Block : Entity {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    abstract override val logic: IBlockEntityLogic
    override val state: EntityState = BlockEntityState()

    companion object {
        val SIZE = PitchPanel.GRID_SIZE
    }
}