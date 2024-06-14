package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.entity.CharacterNetwork
import game.network.messages.FiringHttpMessage

class FireEventForwarder: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val firingEnemy = (extras[0] as CharacterNetwork)
        val direction = firingEnemy.direction
        HttpMessageDispatcher.instance.dispatch(FiringHttpMessage(firingEnemy, direction), private = false)
    }
}