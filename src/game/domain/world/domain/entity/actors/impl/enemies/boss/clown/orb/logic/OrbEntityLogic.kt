package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.logic.EnemyEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb

open class OrbEntityLogic(override val entity: Orb) : EnemyEntityLogic(entity = entity), IOrbEntityLogic {
    override fun doInteract(e: Entity?) {
        if (canInteractWith(e)) {
            attack(e)
        }
        if (isObstacle(e)) {
            attack(entity)
        }
    }

    override fun isObstacle(e: Entity?): Boolean = e == null
    override fun executeCommandQueue() {}

    override fun observerUpdate(arg: Any?) {
        val gameState = arg as Boolean
        if (gameState) moveOrb()
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