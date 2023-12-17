package game.items

import game.BomberManMatch
import game.Bomberman
import game.entity.bomb.Bomb
import game.events.game.UpdateCurrentAvailableBombsEvent
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
        UpdateCurrentAvailableBombsEvent().invoke(owner.currentBombs - 1)

        bombEntity = Bomb(owner)

        val match = Bomberman.getMatch()
        match.addBomb(bombEntity)

        bombEntity.setOnExplodeListener {
            owner.placedBombs--
            match.removeBomb(bombEntity)
            UpdateCurrentAvailableBombsEvent().invoke(owner.currentBombs + 1)
        }

        bombEntity.spawn(true)
        bombEntity.trigger()
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