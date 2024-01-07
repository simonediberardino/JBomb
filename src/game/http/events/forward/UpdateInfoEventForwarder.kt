package game.http.events.forward

import game.entity.models.Entity
import game.events.models.HttpEvent
import game.http.dao.BomberEntityDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.UpdateInfoHttpMessage

class UpdateInfoEventForwarder : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val extras = extras[0] as Map<String, String>
        HttpMessageDispatcher.instance.dispatch(UpdateInfoHttpMessage(extras))
    }
}