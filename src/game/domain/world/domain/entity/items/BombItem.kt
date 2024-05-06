package game.domain.world.domain.entity.items

import game.Bomberman
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.network.events.forward.UpdateInfoEventForwarder
import game.utils.Utility
import game.utils.dev.Log
import game.utils.file_system.Paths.entitiesFolder
import game.utils.time.now

class BombItem : UsableItem() {
    private lateinit var bombEntity: Bomb

    override fun use(): Boolean {
        val match = Bomberman.match
        val isLocalPlayer = owner == match.player

        val isBombPlacementIntervalValid = Utility.timePassed(
                owner.state.lastPlacedBombTime
        ) >= Bomb.PLACE_INTERVAL

        if (!isBombPlacementIntervalValid) {
            Log.e("Cannot place bomb, too early")
            return false
        }

        if (isLocalPlayer && owner.state.placedBombs >= owner.state.maxBombs) {
            Log.e("owner.state.placedBombs: ${owner.state.placedBombs}")
            Log.e("owner.state.maxBombs: ${owner.state.maxBombs}")
            Log.e("Cannot place bomb, placedBombs >= maxBombs")
            return false
        }

        if (isLocalPlayer && owner.state.currentBombs <= 0) {
            Log.e("owner.state.currentBombs: ${owner.state.currentBombs}")
            Log.e("Cannot place bomb, currentBombs <= 0")
            return false
        }

        if (isLocalPlayer && owner.state.currExplosionLength <= 0) {
            Log.e("owner.state.currExplosionLength: ${owner.state.currExplosionLength}")
            Log.e("Cannot place bomb, currExplosionLength <= 0")
            return false
        }

        if (isLocalPlayer && owner.state.currentBombs >= match.currentLevel.info.maxBombs) {
            Log.e("owner.state.currentBombs: ${owner.state.currentBombs}")
            Log.e("Cannot place bomb, currentBombs >= maxBombs")
            return false
        }

        owner.state.lastPlacedBombTime = now()
        owner.state.placedBombs++
        owner.state.bombsSolid = (false)

        if (isLocalPlayer)
            UpdateCurrentAvailableItemsEvent().invoke(owner.state.currentBombs - 1)

        bombEntity = Bomb(owner)

        match.addBomb(bombEntity)
        UpdateInfoEventForwarder().invoke((bombEntity as Entity).toEntityNetwork())

        bombEntity.logic.onExplodeCallback = {
            owner.state.placedBombs--
            match.removeBomb(bombEntity)

            if (isLocalPlayer)
                UpdateCurrentAvailableItemsEvent().invoke(owner.state.currentBombs + 1)
        }

        bombEntity.logic.spawn(forceSpawn = true)
        bombEntity.logic.trigger()

        return true
    }

    override fun combineItems(item: UsableItem) {
    }


    override val imagePath: String = "$entitiesFolder/bomb/bomb_0.png"

    override val count: Int
        get() = owner.state.currentBombs

    override val type: ItemsTypes = ItemsTypes.BombItem
}