package game.network.events.process

import game.Bomberman
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.items.ItemsTypes
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class UseItemHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        Log.i("UseItemHttpEventProcessor info $info")
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val itemType = info.getOrTrim("itemType")?.toInt() ?: return
        Log.i("UseItemHttpEventProcessor data $entityId, $itemType")

        val entity: Entity = Bomberman.match.getEntityById(entityId) ?: return
        val item = ItemsTypes.values()[itemType].toItem()
        Bomberman.match.give(entity as BomberEntity, item)

        item.use()
    }
}