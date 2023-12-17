package game.entity.bomb

import game.Bomberman
import game.entity.models.*
import game.ui.panels.game.PitchPanel
import game.utils.Utility
import game.values.DrawPriority
import java.awt.image.BufferedImage
import java.lang.Exception
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * An abstract class for in-game explosions;
 */
abstract class AbstractExplosion(private val owner: Entity,
                                 coordinates: Coordinates?,
                                 protected val direction: Direction,
                                 private val distanceFromExplosive: Int,
                                 val explosive: Explosive,
                                 private var canExpand: Boolean
) : MovingEntity(coordinates) {
    // The maximum distance from the bomb that the explosion can travel.
    private val maxDistance: Int = explosive.maxExplosionDistance
    private var appearing = true
    private var explosionState = 1
    private var lastRefresh: Long = 0

    constructor(owner: Entity,
                coordinates: Coordinates?,
                direction: Direction,
                explosive: Explosive) : this(owner, coordinates, direction, 0, explosive)

    constructor(owner: Entity,
                coordinates: Coordinates?,
                direction: Direction,
                distanceFromBomb: Int,
                explosive: Explosive) : this(owner, coordinates, direction, distanceFromBomb, explosive, true)

    init {
        //on first (center) explosion
        if (distanceFromExplosive == 0) {
            val desiredCoords = allCoordinates
            Bomberman.getMatch().entities
                    .parallelStream()
                    .filter { e: Entity? -> desiredCoords.stream().anyMatch { coord: Coordinates? -> Coordinates.doesCollideWith(coord, e) } }
                    .forEach { e: Entity? -> interact(e) }
        }

        if (getCanExpand())
            expandBomb(direction, size)
    }

    protected abstract val explosionClass: Class<out AbstractExplosion>
    override fun getDrawPriority(): DrawPriority {
        return DrawPriority.DRAW_PRIORITY_4
    }

    /**
     * Interacts with another entity in the game.
     *
     * @param e The entity to interact with.
     */
    override fun doInteract(e: Entity?) {
        e?.onExplosion(this)
    }

    /**
     * Returns the size of the explosion.
     *
     * @return The size of the explosion.
     */
    override fun getSize(): Int {
        return SIZE
    }

    /**
     * Sets the coordinates of the explosion and creates new explosions based on its distance from the bomb.
     *
     * @param coordinates The new coordinates of the explosion.
     */
    override fun move(coordinates: Coordinates) {
        val nextTopLeftCoords = Coordinates.nextCoords(coords, direction, size)
        try {
            val constructor = explosionClass.getConstructor(
                    Entity::class.java,
                    Coordinates::class.java,
                    Direction::class.java,
                    Int::class.javaPrimitiveType,
                    Explosive::class.java
            )
            constructor.newInstance(
                    owner,
                    nextTopLeftCoords,
                    direction,
                    distanceFromExplosive + 1,
                    explosive
            )!!.explode()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> {
        return HashSet()
    }

    override fun getImageRefreshRate(): Int {
        return 100
    }

    override fun isObstacle(e: Entity?): Boolean {
        return e == null || explosive.isObstacleOfExplosion(e)
    }

    override fun getObstacles(): Set<Class<out Entity>> {
        return HashSet(explosive.explosionObstacles)
    }

    override fun getInteractionsEntities(): Set<Class<out Entity>> {
        return HashSet(explosive.explosionInteractionEntities)
    }

    override fun getImage(): BufferedImage {
        val imageName = when {
            distanceFromExplosive == 0 -> {
                val centralImage = "_central$_state.png"
                "${basePath}$centralImage"
            }
            else -> {
                val isLast = if (canExpand) "" else "_last"
                val imageFileName = "_${direction.toString().lowercase(Locale.getDefault())}"
                val expandedImage = "${basePath}$imageFileName$isLast${_state}.png"
                expandedImage
            }
        }

        return loadAndSetImage(imageName)
    }

    private fun getCanExpand(): Boolean {
        if (distanceFromExplosive >= maxDistance) canExpand = false
        return canExpand
    }

    protected open fun shouldHideCenter(): Boolean {
        return false
    }

    fun onObstacle(coordinates: Coordinates?) {
        try {
            val constructor = explosionClass.getConstructor(
                    Entity::class.java,
                    Coordinates::class.java,
                    Direction::class.java,
                    Int::class.javaPrimitiveType,
                    Explosive::class.java,
                    Boolean::class.javaPrimitiveType
            )
            constructor.newInstance(
                    owner,
                    coordinates,
                    direction,
                    distanceFromExplosive + 1,
                    explosive,
                    false
            )!!.explode()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val _state: Int
        get() {
            if (explosionState == 0 && !appearing) {
                despawn()
                appearing = true
                return 0
            }

            if (explosionState == BOMB_STATES)
                appearing = false

            val appearingConstant = if (!appearing) -1 else 1

            val prevState = explosionState
            val currentTime = System.currentTimeMillis()

            if (currentTime - lastRefresh >= imageRefreshRate) {
                explosionState += appearingConstant
                lastRefresh = currentTime
            }

            return prevState
        }

    private fun expandBomb(d: Direction, stepSize: Int) {
        moveOrInteract(d, stepSize, true)
    }

    fun explode() {
        spawn(true, false)
    }

    companion object {
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        val SPAWN_OFFSET = (PitchPanel.GRID_SIZE - SIZE) / 2
        var MAX_EXPLOSION_LENGTH = 5
        protected const val BOMB_STATES = 3
    }
}