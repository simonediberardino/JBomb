package game.domain.world.domain.entity.items

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

abstract class UsableItem {
    lateinit var owner: BomberEntity
    abstract fun use(itemId: Long? = null): Long
    abstract fun combineItems(item: UsableItem)
    abstract val imagePath: String?
    abstract val count: Int
    abstract val type: ItemsTypes

    fun give() {
        JBomb.match.give(owner, this)
    }

    open fun remove() {
        JBomb.match.removeItem(owner)
    }
}