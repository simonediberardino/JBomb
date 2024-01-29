package game.network.events.forward

import game.engine.events.models.HttpEvent
import game.engine.world.entity.dto.CharacterDto
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.LocationHttpMessage

class LocationUpdatedHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as CharacterDto
        HttpMessageDispatcher.instance.dispatch(LocationHttpMessage(info), info.entityId, true)
    }
}