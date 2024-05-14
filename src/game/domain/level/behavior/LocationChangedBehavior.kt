package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.network.entity.EntityNetwork
import game.network.events.forward.LocationUpdatedHttpEventForwarder
import game.network.gamehandler.ClientGameHandler

class LocationChangedBehavior(private val entityNetwork: EntityNetwork) : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            LocationUpdatedHttpEventForwarder().invoke(entityNetwork, false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            if (entityNetwork.entityId == (JBomb.match.onlineGameHandler as ClientGameHandler).id) {
                // If player is not stored yet
                val player = JBomb.match.player
                if (player == null || !player.state.isSpawned) {
                    (JBomb.match.getEntityById(entityNetwork.entityId) as Player?)?.let {
                        JBomb.match.player = it
                    }
                }

                LocationUpdatedHttpEventForwarder().invoke(entityNetwork, true)
            }
        }
    }
}