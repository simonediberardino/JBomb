package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.LevelInfoHttpMessage
import game.utils.Extensions.getOrTrim

class LevelInfoHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val clientId = info.getOrTrim("id")?.toLong() ?: return
        val levelId = info.getOrTrim("levelId")?.toInt() ?: return
        val worldId = info.getOrTrim("worldId")?.toInt() ?: return

        HttpMessageDispatcher.instance.dispatch(LevelInfoHttpMessage(clientId, levelId, worldId), clientId)
    }
}