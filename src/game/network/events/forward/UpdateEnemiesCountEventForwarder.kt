package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.UpdateEnemiesCountMessage

class UpdateEnemiesCountEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val count = extras[0] as Int
        HttpMessageDispatcher.instance.dispatch(UpdateEnemiesCountMessage(count))
    }
}