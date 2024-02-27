package game.engine.world.domain.entity.actors.impl.blocks.base_block.logic

import game.engine.world.domain.entity.actors.abstracts.base.IEntityLogic
import game.engine.world.domain.entity.actors.abstracts.base.logic.EntityLogic

interface IBlockEntityLogic : IEntityLogic {
    fun destroy()
}