package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.CharacterDao
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.LocationHttpMessage
import game.http.messages.SpawnedEntityHttpMessage

class LocationUpdatedHttpEventForwarder : HttpEvent {
    override fun invoke(info: Any) {
        HttpMessageDispatcher.instance.dispatch(LocationHttpMessage(info as CharacterDao), info.entityId, true)
    }
}