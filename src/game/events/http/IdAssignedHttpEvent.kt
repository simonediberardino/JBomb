package game.events.http

import game.Bomberman
import game.events.models.HttpEvent
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.PlayerJoinRequestHttpMessage

class IdAssignedHttpEvent: HttpEvent {
    override fun invoke(info: Map<String, String>) {
        val id = info["id"]?.toInt()
        println("IdAssignedHttpEvent: $id")

        Bomberman.getMatch().clientGameHandler.id = id ?: return

        // client confirms to join the match after getting id;
        HttpMessageDispatcher.instance.dispatch(PlayerJoinRequestHttpMessage(id))
    }
}