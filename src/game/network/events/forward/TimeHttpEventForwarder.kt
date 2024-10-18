package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.entity.EntityNetwork
import game.network.messages.CollideEventMessage
import game.network.messages.TimeHttpMessage

class TimeHttpEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        HttpMessageDispatcher.instance.dispatch(TimeHttpMessage(extras[0] as Long))
    }
}