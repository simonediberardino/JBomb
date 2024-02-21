package game.network.events.forward

import game.engine.events.models.HttpEvent
import game.engine.world.network.dto.EntityDto
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.UseItemHttpMessage
import game.engine.world.domain.entity.items.ItemsTypes

class UseItemHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val userDao = (extras[0] as EntityDto)
        val itemType = (extras[1] as ItemsTypes)
        HttpMessageDispatcher.instance.dispatch(UseItemHttpMessage(userDao, itemType))
    }
}