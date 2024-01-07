package game.http.events.process

import game.Bomberman
import game.entity.models.Character
import game.entity.models.Entity
import game.entity.player.BomberEntity
import game.events.models.HttpEvent
import game.items.ItemsTypes
import game.utils.Extensions.getOrTrim

class UseItemHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        println("UseItemHttpEventProcessor info $info")
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val itemType = info.getOrTrim("itemType")?.toInt() ?: return
        println("UseItemHttpEventProcessor data $entityId, $itemType")

        val entity: Entity = Bomberman.getMatch().getEntityById(entityId) ?: return
        val item = ItemsTypes.values()[itemType].toItem()
        Bomberman.getMatch().give(entity as BomberEntity, item)

        item.use()
    }
}