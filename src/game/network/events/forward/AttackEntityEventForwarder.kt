package game.network.events.forward

import game.engine.events.models.HttpEvent
import game.engine.world.entity.dto.EntityDto
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.AttackEntityHttpMessage

class AttackEntityEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val victim = extras[0] as EntityDto
        val damage = extras[1] as Int
        HttpMessageDispatcher.instance.dispatch(AttackEntityHttpMessage(victim, damage))
    }
}