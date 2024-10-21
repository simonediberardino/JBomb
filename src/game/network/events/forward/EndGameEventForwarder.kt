package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.EndGameHttpMessage

class EndGameEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        HttpMessageDispatcher.instance.dispatch(EndGameHttpMessage())
    }
}