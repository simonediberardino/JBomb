package game.actorbehavior

import game.Bomberman
import game.entity.player.Player
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.events.forward.LocationUpdatedHttpEventForwarder
import game.http.messages.LocationHttpMessage
import game.level.online.ClientGameHandler

class LocationChangedBehavior(private val entityDao: EntityDao) : GameBehavior {
    override fun hostBehavior(): () -> Unit {
        return {
            LocationUpdatedHttpEventForwarder().invoke(entityDao)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            if (entityDao.entityId == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler).id) {
                // If player is not stored yet
                if (Bomberman.getMatch().player == null) {
                    Bomberman.getMatch().player = Bomberman.getMatch().getEntityById(entityDao.entityId) as Player
                    Bomberman.getMatch().assignPlayerToControllerManager()
                }
            }
        }
    }
}