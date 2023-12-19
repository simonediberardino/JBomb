package game.entity.blocks

import game.Bomberman
import game.entity.bomb.AbstractExplosion
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.powerups.PowerUp
import game.powerups.portal.EndLevelPortal
import game.utils.Utility
import java.awt.image.BufferedImage
import java.lang.Exception

class DestroyableBlock(
        coordinates: Coordinates?,
        public var powerUpClass: Class<out PowerUp>? = null
) : MovableBlock(coordinates) {

    constructor(coordinates: Coordinates?) : this(coordinates, null)

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    override fun doInteract(e: Entity?) {}

    override fun getImage(): BufferedImage {
        return loadAndSetImage(Bomberman.getMatch().currentLevel.info.destroyableBlockImagePath)
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
                powerUp = powerUpClass!!.getConstructor(Coordinates::class.java).newInstance(coords)
                powerUp.spawn(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> {
        return HashSet<Class<out Entity>>(listOf(AbstractExplosion::class.java))
    }

    override fun onExplosion(explosion: AbstractExplosion) {
        explosion.attack(this)
    }

    companion object {
        private const val POWER_UP_SPAWN_CHANGE = 33
    }
}
