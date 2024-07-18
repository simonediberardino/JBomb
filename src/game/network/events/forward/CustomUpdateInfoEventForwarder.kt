package game.network.events.forward

import game.JBomb
import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.UpdateInfoHttpMessage

class CustomUpdateInfoEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val entityId = extras[0] as Long
        val params = extras[1] as Map<String, String>

        HttpMessageDispatcher.instance.dispatch(UpdateInfoHttpMessage(
                entityId = entityId,
                params = params
        ), private = false)

    }
}