package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.DespawnedEntityHttpMessage
import game.http.messages.SpawnedEntityHttpMessage

class DespawnEntityEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as EntityDao
        HttpMessageDispatcher.instance.dispatch(DespawnedEntityHttpMessage(info))
    }
}