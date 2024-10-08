package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class BombExplodedEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val callerId = info.getOrTrim("entityId")?.toLong() ?: return
        val bombId = info.getOrTrim("bombId")?.toLong() ?: return

        Log.e("BombExplodedEventProcessor received $info")

        Log.e("BombExplodedEventProcessor alive entities ${JBomb.match.bombs}")

        val entity = JBomb.match.getEntityById(bombId) as Bomb?
        entity?.logic?.explode()
    }
}