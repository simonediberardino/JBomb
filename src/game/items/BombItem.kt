package game.items

import game.Bomberman
import game.entity.placeable.Bomb
import game.events.game.UpdateCurrentAvailableItemsEvent
import game.utils.Paths.entitiesFolder
import game.utils.Utility.timePassed

class BombItem : UsableItem() {
    private lateinit var bombEntity: Bomb

    override fun use() {
        if (owner.currExplosionLength == 0 || owner.placedBombs >= owner.maxBombs || owner.currentBombs <= 0 || timePassed(owner.lastPlacedBombTime) < Bomb.PLACE_INTERVAL) {
            return
        }

        val match = Bomberman.getMatch() ?: return

        owner.lastPlacedBombTime = System.currentTimeMillis()
        owner.placedBombs++
        owner.setBombsSolid(false)

        if (owner == match.player)
            UpdateCurrentAvailableItemsEvent().invoke(owner.currentBombs - 1)

        bombEntity = Bomb(owner)

        match.addBomb(bombEntity)

        bombEntity.onExplodeCallback = {
            owner.placedBombs--
            match.removeBomb(bombEntity)

            if (owner == match.player)
                UpdateCurrentAvailableItemsEvent().invoke(owner.currentBombs + 1)
        }

        bombEntity.spawn(true)
        bombEntity.trigger()
    }

    override fun combineItems(item: UsableItem) {
    }


    override val imagePath: String
        get() = "$entitiesFolder/bomb/bomb_0.png"

    override val count: Int
        get() = owner.currentBombs
}