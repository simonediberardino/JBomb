package game.network.events.forward

import game.engine.events.models.HttpEvent
import game.network.entity.CharacterNetwork
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.LocationHttpMessage

class LocationUpdatedHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as CharacterNetwork
        HttpMessageDispatcher.instance.dispatch(LocationHttpMessage(info), info.entityId, true)
    }
}