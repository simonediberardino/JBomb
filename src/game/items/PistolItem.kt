package game.items

import game.entity.EntityTypes
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.HardBlock
import game.entity.bomb.AbstractExplosion.Companion.SIZE
import game.entity.placeable.Bomb
import game.entity.bomb.PistolExplosion
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.entity.models.Entity
import game.entity.models.Explosive
import game.events.game.UpdateCurrentAvailableItemsEvent
import game.sound.AudioManager
import game.sound.SoundModel
import game.utils.Paths.itemsPath
import game.utils.Utility.timePassed

class PistolItem : UsableItem(), Explosive {
    private var bullets = 5

    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(
                HardBlock::class.java,
                DestroyableBlock::class.java
        )

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(
                Enemy::class.java,
                Bomb::class.java
        )

    override val imagePath: String
        get() = "$itemsPath/pistol.png"

    override val count: Int
        get() = bullets

    override val type: ItemsTypes = ItemsTypes.PistolItem

    override val maxExplosionDistance: Int
        get() = 3

    private fun setBullets(i: Int) {
        bullets = i
        UpdateCurrentAvailableItemsEvent().invoke(bullets)
    }

    private fun addBullets(i: Int) = setBullets(bullets + i)

    override fun use() {
        if (timePassed(owner.lastPlacedBombTime) < Bomb.PLACE_INTERVAL) {
            return
        }

        owner.lastPlacedBombTime = System.currentTimeMillis()
        addBullets(-1)

        val explosion = PistolExplosion(
                owner,
                Coordinates.nextCoords(owner.coords, owner.currDirection, SIZE),
                owner.currDirection,
                1,
                this
        )

        AudioManager.getInstance().play(SoundModel.EXPLOSION)
        explosion.explode()
        if (bullets == 0) {
            remove()
        }
    }

    override fun combineItems(item: UsableItem) {
        addBullets((item as PistolItem).bullets)
    }
}