package game.domain.world.domain.entity.actors.abstracts.placeable.bomb.logic

import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic

interface IBombLogic: IBlockEntityLogic {
    fun explode()
    fun trigger()
}