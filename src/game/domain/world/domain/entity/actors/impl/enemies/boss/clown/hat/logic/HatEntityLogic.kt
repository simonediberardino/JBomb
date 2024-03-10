package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.logic.OrbEntityLogic

class HatEntityLogic(override val entity: Hat) : OrbEntityLogic(entity = entity) {
    override fun doInteract(e: Entity?) {
        e ?: return

        if (e is Clown) {
            e.state.hasHat = true
            eliminated()
        } else {
            attack(e)
        }
    }

    // Changed
    override fun moveOrb() {
        if (!entity.state.canMove || !entity.logic.isAlive())
            return

        if (entity.state.enhancedDirection == null) {
            // When hitting a wall, bounce and change direction;
            if (!moveOrInteract(entity.state.direction))
                entity.state.direction = entity.state.direction.opposite()

            updateMovementDirection(entity.state.direction)
            return
        }

        entity.state.enhancedDirection?.let {
            for (d in it.toDirection()) {
                entity.state.direction = d
                if (!moveOrInteract(d)) {
                    entity.state.enhancedDirection = entity.state.enhancedDirection!!.opposite(d)
                }
                updateMovementDirection(entity.state.direction)
            }
        }
    }
}