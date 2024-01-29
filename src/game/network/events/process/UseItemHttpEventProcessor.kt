package game.network.events.process

import game.Bomberman
import game.engine.world.entity.impl.models.Entity
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.events.models.HttpEvent
import game.engine.world.items.ItemsTypes
import game.utils.Extensions.getOrTrim
import game.utils.Log

class UseItemHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        Log.i("UseItemHttpEventProcessor info $info")
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val itemType = info.getOrTrim("itemType")?.toInt() ?: return
        Log.i("UseItemHttpEventProcessor data $entityId, $itemType")

        val entity: Entity = Bomberman.getMatch().getEntityById(entityId) ?: return
        val item = ItemsTypes.values()[itemType].toItem()
        Bomberman.getMatch().give(entity as BomberEntity, item)

        item.use()
    }
}