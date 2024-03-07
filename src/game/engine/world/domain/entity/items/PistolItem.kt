package game.engine.world.domain.entity.items

import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.engine.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion.Companion.SIZE
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.actors.impl.explosion.PistolExplosion
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.models.Explosive
import game.engine.events.game.UpdateCurrentAvailableItemsEvent
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.utils.file_system.Paths.itemsPath
import game.utils.Utility.timePassed
import game.utils.time.now

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
        if (timePassed(owner.state.lastPlacedBombTime) < Bomb.PLACE_INTERVAL) {
            return
        }

        owner.state.lastPlacedBombTime = now()
        addBullets(-1)

        ExplosionHandler.instance.process {
            val explosion = PistolExplosion(
                    owner,
                    Coordinates.nextCoords(owner.info.position, owner.state.direction, SIZE),
                    owner.state.direction,
                    1,
                    this
            ).logic.explode()

            listOf(explosion)
        }

        if (bullets == 0) {
            remove()
        }
    }

    override fun combineItems(item: UsableItem) {
        addBullets((item as PistolItem).bullets)
    }
}