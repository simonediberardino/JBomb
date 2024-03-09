package game.network.events.process

import game.Bomberman
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.events.models.HttpEvent
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class AttackEntityEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val victimId = info.getOrTrim("entityId")?.toLong() ?: return
        val damage = info.getOrTrim("damage")?.toInt() ?: return

        Log.i("AttackEntityEventProcessor received $victimId")

        val entity: Entity = Bomberman.getMatch().getEntityById(victimId) ?: return
        entity.logic.onAttackReceived(damage)
    }
}