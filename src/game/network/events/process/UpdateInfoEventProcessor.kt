package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class UpdateInfoEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        Log.i("UpdateInfoEventProcessor: $info")

        val clientId = info.getOrTrim("entityId")?.toLong() ?: return

        val entity = JBomb.match.getEntityById(clientId)
        entity?.updateInfo(info)
    }
}