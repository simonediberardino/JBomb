package game.entity.blocks

import game.Bomberman
import game.entity.EntityTypes
import game.entity.bomb.AbstractExplosion
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.powerups.PowerUp
import game.powerups.portal.EndLevelPortal
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
                powerUp = powerUpClass!!.getConstructor(Coordinates::class.java).newInstance(entityInfo.position)
                powerUp.spawn(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override val basePassiveInteractionEntities: MutableSet<Class<out Entity>>
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
