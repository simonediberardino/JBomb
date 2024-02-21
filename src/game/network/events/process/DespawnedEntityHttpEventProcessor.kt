package game.network.events.process

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.events.models.HttpEvent
import game.utils.Extensions.getOrTrim
import game.utils.Log

class DespawnedEntityHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return

        Log.i("DespawnedEntityHttpEventProcessor received $entityId")

        val entity: Entity = Bomberman.getMatch().getEntityById(entityId) ?: return
        entity.despawn()
    }
}