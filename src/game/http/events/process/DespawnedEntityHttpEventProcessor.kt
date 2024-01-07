package game.http.events.process

import game.Bomberman
import game.entity.models.Entity
import game.events.models.HttpEvent
import game.utils.Extensions.getOrTrim

class DespawnedEntityHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return

        println("DespawnedEntityHttpEventProcessor received $entityId")

        val entity: Entity = Bomberman.getMatch().getEntityById(entityId) ?: return
        entity.despawn()
    }
}