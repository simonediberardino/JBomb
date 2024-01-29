package game.engine.level.behavior

import game.Bomberman
import game.engine.world.entity.impl.player.Player
import game.engine.world.entity.dto.EntityDto
import game.network.events.forward.LocationUpdatedHttpEventForwarder
import game.engine.level.online.ClientGameHandler

class LocationChangedBehavior(private val entityDto: EntityDto) : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            LocationUpdatedHttpEventForwarder().invoke(entityDto)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            if (entityDto.entityId == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler).id) {
                // If player is not stored yet
                if (Bomberman.getMatch().player == null) {
                    val player = Bomberman.getMatch().getEntityById(entityDto.entityId) as Player?
                    if (player != null) {
                        Bomberman.getMatch().player = player
                        Bomberman.getMatch().assignPlayerToControllerManager()
                    }
                }

                LocationUpdatedHttpEventForwarder().invoke(entityDto)
            }
        }
    }
}