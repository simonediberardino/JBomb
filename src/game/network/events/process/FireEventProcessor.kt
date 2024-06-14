package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.FiringEnemy
import game.domain.world.domain.entity.geo.Direction
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class FireEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val firingEntityid = info.getOrTrim("id")?.toLong() ?: return
        val direction = Direction.values()[info.getOrTrim("direction")?.toInt() ?: return]

        Log.i("FireEventProcessor received $firingEntityid")

        val firingEntity = JBomb.match.getEntityById(firingEntityid) ?: return
        if (firingEntity is FiringEnemy) {
            firingEntity.logic.fire(direction)
        }
    }
}