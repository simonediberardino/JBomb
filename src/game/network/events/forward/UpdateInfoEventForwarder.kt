package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.entity.EntityNetwork
import game.network.messages.UpdateInfoHttpMessage

class UpdateInfoEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val entityNetwork = extras[0] as EntityNetwork
        HttpMessageDispatcher.instance.dispatch(UpdateInfoHttpMessage(entityNetwork))
    }
}