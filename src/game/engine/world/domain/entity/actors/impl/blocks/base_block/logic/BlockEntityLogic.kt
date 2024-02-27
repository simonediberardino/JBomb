package game.engine.world.domain.entity.actors.impl.blocks.base_block.logic

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.logic.EntityLogic

abstract class BlockEntityLogic(entity: Entity) : EntityLogic(entity), IBlockEntityLogic {
    override fun destroy() {
        entity.logic.eliminated()
    }

    override fun onAttackReceived(damage: Int) {
        destroy()
    }

    override fun onGameTick(arg: Any?) {}
}