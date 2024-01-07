package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dao.EntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.UseItemHttpMessage
import game.items.ItemsTypes

class UseItemHttpEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val userDao = (extras[0] as EntityDao)
        val itemType = (extras[1] as ItemsTypes)
        HttpMessageDispatcher.instance.dispatch(UseItemHttpMessage(userDao, itemType))
    }
}