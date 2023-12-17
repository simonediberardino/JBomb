package game.items

import game.Bomberman
import game.entity.models.BomberEntity

abstract class UsableItem {
    lateinit var owner: BomberEntity
    abstract fun use()
    abstract val imagePath: String?
    abstract val count: Int
    fun give() {
        Bomberman.getMatch().give(owner, this)
    }

    fun remove() {
        Bomberman.getMatch().removeItem(owner)
    }
}