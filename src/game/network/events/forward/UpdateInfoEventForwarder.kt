package game.network.events.forward

import game.engine.world.network.dto.EntityDto
import game.engine.events.models.HttpEvent
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.UpdateInfoHttpMessage

class UpdateInfoEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val entityDto = extras[0] as EntityDto
        HttpMessageDispatcher.instance.dispatch(UpdateInfoHttpMessage(entityDto))
    }
}