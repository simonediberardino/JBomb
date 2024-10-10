package game.domain.world.domain.entity.actors.impl.blocks.base_block.logic

import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.logic.EntityLogic
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable

open class BlockEntityLogic(entity: Entity) : EntityLogic(entity), IBlockEntityLogic {
    override fun destroy() {
        entity.logic.eliminated()
    }

    override fun damageAnimation() {}

    override fun onAttackReceived(damage: Int, attacker: EntityInteractable) {
        destroy()
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {}

    override fun interact(e: Entity?) {}

    override fun doInteract(e: Entity?) {}
}