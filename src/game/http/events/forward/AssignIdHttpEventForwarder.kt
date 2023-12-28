package game.http.events.forward

import game.events.models.HttpEvent
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.AssignIdHttpMessage
import game.http.serializing.HttpParserSerializer
import game.utils.Extensions.getOrTrim

class AssignIdHttpEventForwarder : HttpEvent {
    override fun invoke(info: Map<String, String>) {
        val id = info.getOrTrim("id")?.toInt() ?: return
        HttpMessageDispatcher.instance.dispatch(AssignIdHttpMessage(id), id)
    }
}