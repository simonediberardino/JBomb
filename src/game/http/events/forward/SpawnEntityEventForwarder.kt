package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.LevelInfoHttpMessage
import game.http.messages.SpawnedEntityHttpMessage
import game.utils.Extensions.getOrTrim
import game.utils.Log

class SpawnEntityEventForwarder(private val clientId: Long) : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as EntityDao
        val entityExtras = extras[1] as Map<String, String>
        HttpMessageDispatcher.instance.dispatch(SpawnedEntityHttpMessage(info, entityExtras), clientId)
    }
}