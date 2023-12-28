package game.http.events.process

import game.Bomberman
import game.events.models.HttpEvent
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.PlayerJoinRequestHttpMessage
import game.level.online.ClientGameHandler
import game.utils.Extensions.getOrTrim

class IdAssignedHttpEventProcessor : HttpEvent {
    override fun invoke(info: Map<String, String>) {
        val id = info.getOrTrim("id")?.toInt() ?: return

        println("IdAssignedHttpEventProcessor: $info, }}")

        (Bomberman.getMatch().onlineGameHandler as ClientGameHandler).id = id.toLong()

        // client confirms to join the match after getting id;
        HttpMessageDispatcher.instance.dispatch(PlayerJoinRequestHttpMessage(id))
    }
}