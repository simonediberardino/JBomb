package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.AttackEntityHttpMessage

class AttackEntityEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val victim = extras[0] as EntityDao
        val damage = extras[1] as Int
        HttpMessageDispatcher.instance.dispatch(AttackEntityHttpMessage(victim, damage))
    }
}