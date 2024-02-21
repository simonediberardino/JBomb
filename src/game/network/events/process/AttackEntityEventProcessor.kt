package game.network.events.process

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.events.models.HttpEvent
import game.utils.Extensions.getOrTrim
import game.utils.Log

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