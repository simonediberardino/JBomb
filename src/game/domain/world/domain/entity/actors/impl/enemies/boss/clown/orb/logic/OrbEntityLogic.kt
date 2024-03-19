package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.AiEnemyLogic

open class OrbEntityLogic(override val entity: Orb) : AiEnemyLogic(entity = entity), IOrbEntityLogic {
    override fun doInteract(e: Entity?) {
        if (canInteractWith(e)) {
            attack(e)
        }
        if (isObstacle(e)) {
            attack(entity)
        }
    }

    override fun process() {
        moveOrb()
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

    override fun isObstacle(e: Entity?): Boolean = e == null
}