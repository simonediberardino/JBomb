package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.logic

import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb

open class OrbEntityLogic(override val entity: Orb) : AiLogic(entity = entity), IOrbEntityLogic {
    override fun process() {
        moveOrb()
    }

    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let {
            attack(it)
        }
    }

    override fun moveOrb() {
        if (!entity.state.canMove || !entity.logic.isAlive())
            return

        if (entity.state.enhancedDirection == null) {
            move(entity.state.direction)
            return
        }

        entity.state.enhancedDirection?.let {
            for (d in it.toDirection()) {
                moveOrInteract(d)
            }
        }
    }
}