package game.items

import game.Bomberman
import game.entity.bomb.Bomb
import game.events.game.UpdateCurrentAvailableItemsEvent
import game.utils.Paths.entitiesFolder
import game.utils.Utility.timePassed

class BombItem : UsableItem() {
    private lateinit var bombEntity: Bomb
    override fun use() {
        if (owner.currExplosionLength == 0 || owner.placedBombs >= owner.maxBombs || owner.currentBombs <= 0 || timePassed(owner.lastPlacedBombTime) < Bomb.PLACE_INTERVAL) {
            return
        }

        owner.lastPlacedBombTime = System.currentTimeMillis()
        owner.placedBombs++
        owner.setBombsSolid(false)
        UpdateCurrentAvailableItemsEvent().invoke(owner.currentBombs - 1)

        bombEntity = Bomb(owner)

        val match = Bomberman.getMatch()
        match.addBomb(bombEntity)

        bombEntity.setOnExplodeListener {
            owner.placedBombs--
            match.removeBomb(bombEntity)
            UpdateCurrentAvailableItemsEvent().invoke(owner.currentBombs + 1)
        }

        bombEntity.spawn(true)
        bombEntity.trigger()
    }

    override fun combineItems(item: UsableItem) {
        TODO("Not yet implemented")
    }


    override val imagePath: String
        get() {
            return "$entitiesFolder/bomb/bomb_0.png"
        }

    override val count: Int
        get() {
            return owner.currentBombs
        }
}