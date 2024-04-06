package game.domain.world.domain.entity.actors.impl.placeable.bomb.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.FireExplosion
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Direction
import java.util.*

class BombLogic(override val entity: Bomb) : BlockEntityLogic(entity = entity), IBombLogic {
    var onExplodeCallback: (() -> Unit)? = null

    /**
     * Handles the interaction between the bomb and another entity.
     *
     * @param e The entity to interact with.
     */
    override fun doInteract(e: Entity?) {}

    override fun onExplosion(explosion: AbstractExplosion?) {
        super.onExplosion(explosion)
        explode()
    }

    override fun explode() {
        if (!entity.state.isSpawned)
            return

        eliminated()

        // TODO CHANGED
        ExplosionHandler.instance.process {
            // Trigger explosions in all directions
            Direction.values().map {
                FireExplosion(
                        entity.state.caller,
                        entity.info.position,
                        it,
                        entity
                ).logic.explode()
            }
        }

        // Invoke the explosion callback if set
        onExplodeCallback?.invoke()
    }

    override fun trigger() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                explode()
            }
        }, Bomb.EXPLODE_TIMER.toLong())
    }

    /**
     * Handles mouse click interaction with the bomb.
     */
    override fun onMouseClickInteraction() {
        explode()
    }

    /**
     * Destroys the bomb, initiating the explosion.
     */
    override fun destroy() = explode()
}