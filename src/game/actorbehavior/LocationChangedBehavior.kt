package game.actorbehavior

import game.Bomberman
import game.entity.Player
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.LocationHttpMessage
import game.level.online.ClientGameHandler

class LocationChangedBehavior(private val entityDao: EntityDao) : GameBehavior {
    override fun hostBehavior(): () -> Unit {
        return {
            println("LocationChangedBehavior: hostBehavior $entityDao")
            HttpMessageDispatcher.instance.dispatch(LocationHttpMessage(entityDao))
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            if (entityDao.entityId == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler).id) {
                HttpMessageDispatcher.instance.dispatch(LocationHttpMessage(entityDao))
                println("LocationChangedBehavior: clientBehavior $entityDao")

                // If player is not stored yet
                if (Bomberman.getMatch().player == null) {
                    Bomberman.getMatch().player = Bomberman.getMatch().getEntityById(entityDao.entityId) as Player
                    Bomberman.getMatch().assignPlayerToControllerManager()
                }
            }
        }
    }
}