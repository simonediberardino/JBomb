package game.engine.world.domain.entity.pickups.powerups

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.impl.player.Player
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import java.util.*

/**
 * The abstract PowerUp class is a superclass for all power-ups in the game.
 */
abstract class PowerUp : EntityInteractable {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    // Whether the power-up has already been applied or not
    private var applied = false
    val incompatiblePowerUps = mutableListOf<Class<out PowerUp>>()

    // The character the power-up is applied to
    private var character: Character? = null

    /**
     * Returns the duration of the power-up in milliseconds.
     *
     * @return the duration of the power-up
     */
    open val duration: Int = DEFAULT_DURATION_SEC

    /**
     * Applies the power-up to the specified BomberEntity.
     *
     * @param entity the BomberEntity to apply the power-up to
     */
    fun apply(entity: BomberEntity) {
        if (applied || !canPickUp(entity)) return

        applied = true
        despawnAndNotify()
        character = entity
        doApply(entity)

        val matchPanel = Bomberman.getBombermanFrame().matchPanel
        AudioManager.getInstance().play(SoundModel.POWERUP)

        entity.activePowerUps.add(this.javaClass)

        if (isDisplayable) matchPanel.refreshPowerUps(entity.activePowerUps)

        val durationMillis: Long = duration * 1000L

        // If the power-up has a duration, schedule a TimerTask to cancel it when the duration is up
        if (durationMillis <= 0) return

        val thisPowerUp = this
        val task = object : TimerTask() {
            override fun run() {
                val match = Bomberman.getMatch()
                if (match == null || !match.gameState) return

                entity.removeActivePowerUp(thisPowerUp)

                if (isDisplayable) matchPanel.refreshPowerUps(entity.activePowerUps)

                thisPowerUp.cancel(entity)
            }
        }

        Timer().schedule(task, durationMillis)
    }


    /**
     * Applies the power-up to the specified BomberEntity. This method should be implemented by the subclasses.
     *
     * @param entity the BomberEntity to apply the power-up to
     */
    protected abstract fun doApply(entity: BomberEntity)

    /**
     * Cancels the power-up applied to the specified BomberEntity. This method should be implemented by the subclasses.
     *
     * @param entity the BomberEntity to cancel the power-up for
     */
    protected abstract fun cancel(entity: BomberEntity)

    /**
     * Returns the size of the PowerUp entity.
     *
     * @return the size of the PowerUp entity
     */
    override val size: Int
        get() = PitchPanel.COMMON_DIVISOR * 2

    override fun doInteract(e: Entity?) {
        this.apply(e as BomberEntity)
    }

    override fun getInteractionsEntities(): Set<Class<out Entity>> {
        return hashSetOf(Player::class.java)
    }

    override fun getObstacles(): Set<Class<out Entity>> = emptySet()

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf(Player::class.java)

    open val isDisplayable: Boolean
        get() = true

    /**
     * @return wheter the powerup can be picked up indefinite times or not;
     */
    open fun canPickUp(entity: BomberEntity): Boolean {
        return !entity.activePowerUps.stream().anyMatch { p: Class<out PowerUp> -> p == this.javaClass || incompatiblePowerUps.contains(p) }
    }

    companion object {
        // A static array of power-up classes
        val POWER_UPS: Array<Class<out PowerUp>> = arrayOf(
                PistolPowerUp::class.java,
                ArmorPowerUp::class.java,
                FirePowerUp::class.java,
                SpeedPowerUp::class.java,
                TransparentDestroyableBlocksPowerUp::class.java,
                LivesPowerUp::class.java,
                RemoteControlPowerUp::class.java,
                HammerPowerUp::class.java,
                BlockMoverPowerUp::class.java,
                IncreaseMaxBombsPowerUp::class.java,
                TransparentBombsPowerUp::class.java
        )

        // The default duration for a power-up, in seconds
        const val DEFAULT_DURATION_SEC: Int = 15

    }
}