package game.engine.world.domain.entity.actors.impl.blocks

import game.Bomberman
import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomb.AbstractExplosion
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.pickups.powerups.PowerUp
import game.engine.world.domain.entity.pickups.portals.EndLevelPortal
import game.utils.Utility
import java.awt.image.BufferedImage
import java.lang.Exception

class DestroyableBlock : MovableBlock {
    var powerUpClass: Class<out PowerUp>? = null

    constructor(coordinates: Coordinates?, powerUpClass: Class<out PowerUp>? = null) : super(coordinates) {
        this.powerUpClass = powerUpClass
    }

    constructor(coordinates: Coordinates?) : this(coordinates, null)

    constructor(id: Long) : super(id)

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    override fun doInteract(e: Entity?) {}

    override fun getImage(): BufferedImage {
        return loadAndSetImage(Bomberman.getMatch().currentLevel!!.info.destroyableBlockImagePath)!!
    }

    override fun onDespawn() {
        super.onDespawn()
        if (powerUpClass == null) {
            return
        }

        val spawnPercentage = if (powerUpClass == EndLevelPortal::class.java)
            100
        else
            POWER_UP_SPAWN_CHANGE

        Utility.runPercentage(spawnPercentage) {
            val powerUp: PowerUp
            try {
                powerUp = powerUpClass!!.getConstructor(Coordinates::class.java).newInstance(info.position)
                powerUp.spawn(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf(AbstractExplosion::class.java)

    override fun onExplosion(explosion: AbstractExplosion?) {
        explosion?.attack(this)
    }

    override val type: EntityTypes
        get() = EntityTypes.DestroyableBlock

    companion object {
        private const val POWER_UP_SPAWN_CHANGE = 33
    }
}
