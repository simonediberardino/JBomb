package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.LevelInfoHttpMessage
import game.http.messages.SpawnedEntityHttpMessage
import game.utils.Extensions.getOrTrim

class SpawnEntityEventForwarder(private val clientId: Long) : HttpEvent {
    override fun invoke(info: Any) {
        HttpMessageDispatcher.instance.dispatch(SpawnedEntityHttpMessage(info as EntityDao), clientId)
    }
}