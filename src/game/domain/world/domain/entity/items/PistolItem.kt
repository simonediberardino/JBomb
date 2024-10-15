package game.domain.world.domain.entity.items

import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion.Companion.SIZE
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb
import game.domain.world.domain.entity.actors.impl.explosion.PistolExplosion
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.models.Explosive
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.LavaBlock
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.domain.world.domain.entity.pickups.powerups.PistolPowerUp
import game.utils.file_system.Paths.itemsPath
import game.utils.Utility.timePassed
import game.utils.time.now

class PistolItem(private var bullets: Int = 5) : UsableItem(), Explosive {
    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(
                HardBlock::class.java,
                DestroyableBlock::class.java
        )

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(
                BomberEntity::class.java,
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

    override val whiteListObstacles: Set<Class<out Entity>>
        get() = hashSetOf(LavaBlock::class.java)

    private fun setBullets(i: Int) {
        bullets = i
        UpdateCurrentAvailableItemsEvent().invoke(bullets)
    }

    private fun addBullets(i: Int) = setBullets(bullets + i)

    override fun use(itemId: Long?): Long {
        if (timePassed(owner.state.lastPlacedBombTime) < Bomb.PLACE_INTERVAL) {
            return -1
        }

        owner.state.lastPlacedBombTime = now()

        if (bullets < Integer.MAX_VALUE)
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

        return 1
    }

    override fun remove() {
        super.remove()
        owner.state.activePowerUps.remove(PistolPowerUp::class.java)
    }

    override fun combineItems(item: UsableItem) {
        if (bullets != -1)
            addBullets((item as PistolItem).bullets)
    }
}