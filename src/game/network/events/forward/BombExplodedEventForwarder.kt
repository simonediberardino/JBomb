package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.entity.EntityNetwork
import game.network.messages.BombExplodedHttpMessage
import game.network.messages.CollideEventMessage

class BombExplodedEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val victim = extras[0] as EntityNetwork
        val self = extras[1] as EntityNetwork
        HttpMessageDispatcher.instance.dispatch(BombExplodedHttpMessage(victim, self), private = false)
    }
}