package game.network.events.process

import game.JBomb
import game.domain.events.game.EntityKilledByGameEvent
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.FiringEnemy
import game.domain.world.domain.entity.geo.Direction
import game.network.models.HttpMessageTypes
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class EntityKilledByEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val victimId = info.getOrTrim("victimId")?.toLong() ?: return
        val attackerId = info.getOrTrim("attackerId")?.toLong() ?: return

        Log.i("EntityKilledByEventProcessor received $victimId $attackerId")

        val victimEntity = JBomb.match.getEntityById(victimId) ?: return
        val attackerEntity = JBomb.match.getEntityById(attackerId) ?: return

        EntityKilledByGameEvent().invoke(victimEntity, attackerEntity)
    }
}