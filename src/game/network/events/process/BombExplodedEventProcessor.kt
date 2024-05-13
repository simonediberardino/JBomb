package game.network.events.process

import game.Bomberman
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class BombExplodedEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val callerId = info.getOrTrim("entityId")?.toLong() ?: return
        val bombId = info.getOrTrim("bombId")?.toLong() ?: return

        Log.e("BombExplodedEventProcessor received $info")

        Log.e("BombExplodedEventProcessor alive entities ${Bomberman.match.bombs}")

        val entity = Bomberman.match.bombs.find { it.info.id == bombId }
        entity?.logic?.explode()
    }
}