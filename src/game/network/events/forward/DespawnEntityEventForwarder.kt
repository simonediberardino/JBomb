package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.entity.EntityNetwork
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.DespawnedEntityHttpMessage

class DespawnEntityEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as EntityNetwork
        HttpMessageDispatcher.instance.dispatch(DespawnedEntityHttpMessage(info), private = false)
    }
}