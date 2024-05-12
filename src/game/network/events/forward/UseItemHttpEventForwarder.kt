package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.entity.EntityNetwork
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.UseItemHttpMessage
import game.domain.world.domain.entity.items.ItemsTypes

class UseItemHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val userDao = (extras[0] as EntityNetwork)
        val itemType = (extras[1] as ItemsTypes)
        HttpMessageDispatcher.instance.dispatch(UseItemHttpMessage(userDao, itemType), private = false)
    }
}