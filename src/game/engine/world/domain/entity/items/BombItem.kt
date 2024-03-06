package game.engine.world.domain.entity.items

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.events.game.UpdateCurrentAvailableItemsEvent
import game.utils.file_system.Paths.entitiesFolder
import game.utils.Utility.timePassed
import game.utils.time.now

class BombItem : UsableItem() {
    private lateinit var bombEntity: Bomb

    override fun use() {
        val match = Bomberman.getMatch() ?: return

        val isLocalPlayer = owner == match.player
        val isBombPlacementValid = isLocalPlayer &&
                (owner.currExplosionLength == 0 || owner.placedBombs >= owner.maxBombs || owner.currentBombs <= 0)

        val isBombPlacementIntervalValid = timePassed(owner.lastPlacedBombTime) < Bomb.PLACE_INTERVAL

        if (isBombPlacementValid || isBombPlacementIntervalValid) {
            return
        }

        owner.lastPlacedBombTime = now()
        owner.placedBombs++
        owner.setBombsSolid(false)

        if (isLocalPlayer)
            UpdateCurrentAvailableItemsEvent().invoke(owner.currentBombs - 1)

        bombEntity = Bomb(owner)

        match.addBomb(bombEntity)

        bombEntity.onExplodeCallback = {
            owner.placedBombs--
            match.removeBomb(bombEntity)

            if (isLocalPlayer)
                UpdateCurrentAvailableItemsEvent().invoke(owner.currentBombs + 1)
        }

        bombEntity.spawn(true)
        bombEntity.trigger()
    }

    override fun combineItems(item: UsableItem) {
    }


    override val imagePath: String = "$entitiesFolder/bomb/bomb_0.png"

    override val count: Int
        get() = owner.currentBombs

    override val type: ItemsTypes = ItemsTypes.BombItem
}