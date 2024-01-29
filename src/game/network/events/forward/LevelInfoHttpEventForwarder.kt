package game.network.events.forward

import game.engine.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.LevelInfoHttpMessage
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