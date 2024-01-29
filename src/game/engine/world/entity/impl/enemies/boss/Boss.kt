package game.engine.world.entity.impl.enemies.boss

import game.Bomberman
import game.engine.world.entity.impl.enemies.npcs.IntelligentEnemy
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity
import game.engine.world.pickups.powerups.PowerUp
import game.engine.world.pickups.portals.EndLevelPortal
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import game.values.DrawPriority
import java.util.*

/**
 * An abstract class for enemy bosses;
 */
abstract class Boss : IntelligentEnemy {
    protected var currRageStatus = 0
    protected var healthStatusMap = TreeMap(healthStatusMap())

    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
        move((Coordinates.randomCoordinatesFromPlayer(size, size * 2)))
    }

    init {
        super.setMaxHp(Bomberman.getMatch().currentLevel!!.info.bossMaxHealth)
        super.setAttackDamage(1000)
    }

    override fun onEliminated() {
        super.onEliminated()
        AudioManager.getInstance().play(SoundModel.BOSS_DEATH)
    }

    override val drawPriority: DrawPriority
        get() = DrawPriority.DRAW_PRIORITY_3

    final override val size: Int
        get() = SIZE

    override fun getObstacles(): Set<Class<out Entity>> = interactionsEntities

    protected open fun getImageFromRageStatus(): String = imagePath

    protected abstract fun healthStatusMap(): Map<Int, Int>?

    override fun onDespawn() {
        super.onDespawn()
        val endLevelPortal: PowerUp = EndLevelPortal(Coordinates.generateCoordinatesAwayFromPlayer())
        endLevelPortal.spawn(true)
    }

    /**
     * Updates the rage status of the Boss, loading and setting the corresponding image.
     *
     * @param status the new rage status to be set.
     */
    protected fun updateRageStatus(status: Int) {
        // If the new rage status is the same as the current one, nothing to update.
        if (status == currRageStatus)
            return

        currRageStatus = status
        // Load and set the image.
        loadAndSetImage(getImageFromRageStatus())
    }

    companion object {
        protected var SIZE = PitchPanel.GRID_SIZE * 4
    }
}
