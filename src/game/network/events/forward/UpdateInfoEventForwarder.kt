package game.network.events.forward

import game.domain.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.entity.EntityNetwork
import game.network.messages.UpdateInfoHttpMessage
import game.utils.dev.Extensions.toMap
import game.utils.dev.Log

class UpdateInfoEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val entityNetwork = extras[0] as EntityNetwork
        Log.e("${entityNetwork.toMap()}")
        HttpMessageDispatcher.instance.dispatch(UpdateInfoHttpMessage(entityNetwork))
    }
}