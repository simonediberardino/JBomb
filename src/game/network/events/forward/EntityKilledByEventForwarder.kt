package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.EntityKilledByHttpMessage

class EntityKilledByEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val victim = extras[0] as Entity
        val attacker = extras[1] as Entity

        HttpMessageDispatcher.instance.dispatch(EntityKilledByHttpMessage(
            victim = victim.toEntityNetwork(),
            attacker = attacker.toEntityNetwork()
        ), private = false)
    }
}