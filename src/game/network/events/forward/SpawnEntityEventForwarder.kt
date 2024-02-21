package game.network.events.forward

import game.engine.events.models.HttpEvent
import game.engine.world.network.dto.EntityDto
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.SpawnedEntityHttpMessage

class SpawnEntityEventForwarder(private val clientId: Long) : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as EntityDto
        HttpMessageDispatcher.instance.dispatch(SpawnedEntityHttpMessage(info), clientId)
    }
}