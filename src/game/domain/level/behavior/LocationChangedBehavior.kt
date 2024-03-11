package game.domain.level.behavior

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.network.entity.EntityNetwork
import game.network.events.forward.LocationUpdatedHttpEventForwarder
import game.network.gamehandler.ClientGameHandler

class LocationChangedBehavior(private val entityNetwork: EntityNetwork) : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            LocationUpdatedHttpEventForwarder().invoke(entityNetwork)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            if (entityNetwork.entityId == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler).id) {
                // If player is not stored yet
                if (Bomberman.getMatch().player == null) {
                    val player = Bomberman.getMatch().getEntityById(entityNetwork.entityId) as Player?
                    if (player != null) {
                        Bomberman.getMatch().player = player
                    }
                }

                LocationUpdatedHttpEventForwarder().invoke(entityNetwork)
            }
        }
    }
}