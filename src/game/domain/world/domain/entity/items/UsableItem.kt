package game.domain.world.domain.entity.items

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

abstract class UsableItem {
    lateinit var owner: BomberEntity
    abstract fun use()
    abstract fun combineItems(item: UsableItem)
    abstract val imagePath: String?
    abstract val count: Int
    abstract val type: ItemsTypes

    fun give() {
        Bomberman.getMatch().give(owner, this)
    }

    fun remove() {
        Bomberman.getMatch().removeItem(owner)
    }
}