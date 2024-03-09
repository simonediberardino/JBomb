package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.entity.EntityNetwork
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.SpawnedEntityHttpMessage

class SpawnEntityEventForwarder(private val clientId: Long) : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as EntityNetwork
        HttpMessageDispatcher.instance.dispatch(SpawnedEntityHttpMessage(info), clientId)
    }
}