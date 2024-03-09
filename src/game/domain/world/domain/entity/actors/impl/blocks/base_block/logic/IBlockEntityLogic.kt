package game.domain.world.domain.entity.actors.impl.blocks.base_block.logic

import game.domain.world.domain.entity.actors.abstracts.base.IEntityLogic

interface IBlockEntityLogic : IEntityLogic {
    fun destroy()
}