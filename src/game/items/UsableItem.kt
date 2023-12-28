package game.items

import game.Bomberman
import game.entity.player.BomberEntity

abstract class UsableItem {
    lateinit var owner: BomberEntity
    abstract fun use()
    abstract fun combineItems(item: UsableItem)
    abstract val imagePath: String?
    abstract val count: Int
    fun give() {
        Bomberman.getMatch().give(owner, this)
    }

    fun remove() {
        Bomberman.getMatch().removeItem(owner)
    }
}