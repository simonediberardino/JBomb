package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.CharacterDao
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.LocationHttpMessage
import game.http.messages.SpawnedEntityHttpMessage

class LocationUpdatedHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as CharacterDao
        HttpMessageDispatcher.instance.dispatch(LocationHttpMessage(info), info.entityId, true)
    }
}