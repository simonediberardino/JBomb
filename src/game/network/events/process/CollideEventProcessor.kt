package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.utils.dev.Extensions.getOrTrim

class CollideEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val victimId = info.getOrTrim("victimId")?.toLong() ?: return
        val selfId = info.getOrTrim("selfId")?.toLong() ?: return

        val victim = JBomb.match.getEntityById(victimId)
        val self = JBomb.match.getEntityById(selfId)

        victim?.logic?.interact(self)
    }
}