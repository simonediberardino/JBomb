package game.engine.world.domain.entity.actors.impl.blocks.base_block.logic

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.logic.EntityLogic

open class BlockEntityLogic(entity: Entity) : EntityLogic(entity), IBlockEntityLogic {
    override fun destroy() {
        entity.logic.eliminated()
    }

    override fun onAttackReceived(damage: Int) {
        destroy()
    }

    override fun observerUpdate(arg: Any?) {}

    override fun interact(e: Entity?) {}

    override fun doInteract(e: Entity?) {}
}