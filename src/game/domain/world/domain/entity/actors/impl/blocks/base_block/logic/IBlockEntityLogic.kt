package game.domain.world.domain.entity.actors.impl.blocks.base_block.logic

import game.domain.world.domain.entity.actors.abstracts.base.IEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block

interface IBlockEntityLogic : IEntityLogic {
    fun destroy()
    suspend fun trigger(updatedBlocks: MutableList<Block> = mutableListOf())
    suspend fun onTrigger() {}
    fun modify(block: Block) {}
}