package game.domain.world.domain.entity.actors.impl.placeable.bomb.logic

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.FireExplosion
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.network.events.forward.BombExplodedEventForwarder
import kotlinx.coroutines.launch
import java.util.*

class BombLogic(override val entity: Bomb) : BlockEntityLogic(entity = entity), IBombLogic {
    private var exploded: Boolean = false
    var onExplodeCallback: (() -> Unit)? = null

    /**
     * Handles the interaction between the bomb and another entity.
     *
     * @param e The entity to interact with.
     */
    override fun doInteract(e: Entity?) {
        explode()
    }

    override fun onSpawn() {
        super.onSpawn()
        JBomb.match.gameTickerObservable?.register(entity)
    }

    override fun notifySpawn() {}

    override fun onDespawn() {
        super.onDespawn()
        JBomb.match.gameTickerObservable?.unregister(entity)
    }

    override fun notifyDespawn() {}

    override fun onAdded() {
        super.onAdded()
        JBomb.match.addBomb(entity)
    }

    override fun onRemoved() {
        super.onRemoved()
        JBomb.match.removeBomb(entity)
    }

    override fun onExplosion(explosion: AbstractExplosion?) {
        super.onExplosion(explosion)
        explode()
    }

    override fun explode() {
        if (exploded || JBomb.isGameEnded) {
            return
        }
        
        exploded = true
        eliminated()

        JBomb.match.scope.launch {
            // TODO CHANGED
            ExplosionHandler.instance.process {
                val currCoords = entity.info.position
                //trigger central explosion (ONLY ONE unlike before)
                val centralExplosion = FireExplosion(
                        entity.state.caller,
                        currCoords,
                        Direction.DOWN,//default value, any can be
                        entity
                ).logic.explode()
                // Trigger explosions in all directions
                Direction.values().map {
                    val newCoords = when (it) {
                        Direction.DOWN -> currCoords.plus(Coordinates(0, entity.state.size))
                        Direction.LEFT -> currCoords.plus(Coordinates(-entity.state.size, 0))
                        Direction.UP -> currCoords.plus(Coordinates(0, -entity.state.size))
                        Direction.RIGHT -> currCoords.plus(Coordinates(entity.state.size, 0))
                    }
                    FireExplosion(
                            entity.state.caller,
                            newCoords,
                            it,
                            entity
                    ).logic.explode(centralExplosion)
                }
            }

            // Invoke the explosion callback if set
            onExplodeCallback?.invoke()
        }
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
        BombExplodedEventForwarder().invoke(entity.state.caller.toEntityNetwork(), entity.toEntityNetwork())
    }

    /**
     * Destroys the bomb, initiating the explosion.
     */
    override fun destroy() = explode()
}