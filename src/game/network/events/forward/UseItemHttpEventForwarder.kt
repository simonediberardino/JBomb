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
        val itemId = (extras[2] as Long)
        HttpMessageDispatcher.instance.dispatch(UseItemHttpMessage(userDao, itemType, itemId), private = false)
    }
}