package game.engine.world.domain.entity.actors.impl.enemies.boss.clown

import game.Bomberman
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.actors.impl.explosion.ConfettiExplosion
import game.engine.world.domain.entity.actors.impl.enemies.boss.Boss
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ClownNose
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.engine.world.domain.entity.actors.impl.models.*
import game.engine.world.domain.entity.graphics.*
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.domain.entity.geo.EnhancedDirection
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.utils.Paths
import game.utils.Utility
import java.util.*

/**
 * The Clown class represents a type of Boss entity that implements the Explosive interface.
 * It has a boolean property hasHat that determines whether the Clown is wearing a hat or not.
 * The Clown entity can spawn orbs, enhanced orbs, explosions and throw its hat in random directions.
 */
class Clown : Boss, Explosive {
    private var attackDelay = 5000
    private var lastAttackTime: Long = 0

    /**
     * The hasHat property represents whether the Clown entity is wearing a hat or not.
     */
    var hasHat = false

    /**
     * Constructor for the Clown entity that takes in the clown boss's starting coordinates and sets its initial hasHat value to true.
     *
     * @param coordinates The starting coordinates of the Clown entity.
     */
    constructor(coordinates: Coordinates?) : super(coordinates)

    // TODO REFACTOR
    constructor() : super(null) {
        hitboxSizeToWidthRatio = RATIO_WIDTH
        hasHat = true

        val panelSize = Bomberman
                .getBombermanFrame()
                .pitchPanel
                .preferredSize

        val y = panelSize.getHeight().toInt() - size
        val x = (panelSize.getWidth() / 2 - size / 2).toInt()
        info.position = Coordinates(x, y)
    }

    /**
     * Overrides the getBaseSkins method from the Boss superclass to return an array of skin paths based on the hasHat value.
     *
     * @return A String array of skin paths for the Clown entity.
     */
    override fun getCharacterOrientedImages(): Array<String> = arrayOf(getImageFromRageStatus())

    /**
     * Returns the image path of the Boss corresponding to its current rage status.
     *
     * @return the image path of the Boss.
     */
    override fun getImageFromRageStatus(): String =
            // Format the skin path template with the appropriate values.
            String.format(SKIN_PATH_TEMPLATE, Paths.enemiesFolder, if (hasHat) 1 else 0, currRageStatus)

    /**
     * Returns a list with the supported directions of the Boss.
     *
     * @return the list with the supported directions.
     */
    override fun getSupportedDirections(): List<Direction> = listOf(Direction.LEFT, Direction.RIGHT)

    /**
     * Returns a map with the health percentages and their corresponding rage statuses for the Boss.
     * Note: avoid calling this method to prevent unnecessary memory usage, use the property instead.
     *
     * @return the health status map.
     */
    override fun healthStatusMap(): Map<Int, Int> {
        // Create a new TreeMap with reverse order.
        val map = TreeMap<Int, Int>(Collections.reverseOrder())
        // Add the health percentages and their corresponding rage statuses to the map.
        map[75] = 0
        map[60] = 1
        map[50] = 2
        map[25] = 3
        return map
    }
    /**
     * Returns the top padding of the Boss hitbox.
     *
     * @return the top padding of the hitbox.
     */
    /**
     * Returns the height to hitbox size ratio of the Boss.
     *
     * @return the height to hitbox size ratio.
     */
    override var hitboxSizeToHeightRatio: Float = RATIO_HEIGHT_WITH_HAT
        get() {
            // Set the height to hitbox size ratio based on whether the Boss has a hat or not.
            field = if (hasHat) RATIO_HEIGHT_WITH_HAT else RATIO_HEIGHT
            return field
        }

    override fun getHitboxSizeToHeightRatio(path: String): Float {
        hitboxSizeToHeightRatio = if (isHatImage(path)) RATIO_HEIGHT_WITH_HAT else RATIO_HEIGHT
        return hitboxSizeToHeightRatio
    }

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = super.interactionEntities.apply {
            add(Hat::class.java)
        }

    /**
     * @return A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    private fun isHatImage(path: String): Boolean {
        val tokens = path.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (tokens.size <= 1) false else tokens[1] == "1"
    }


    /**
     * Overrides the getExplosionObstacles method from the Explosive interface to return an empty list.
     *
     * @return An empty List object.
     */
    override val explosionObstacles: Set<Class<out Entity>>
        get() = emptySet()

    /**
     * Returns a list of entity classes that can interact with explosions.
     *
     * @return a list of entity classes that can interact with explosions.
     */
    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(
                Player::class.java,
                Bomb::class.java
        )

    /**
     * Returns the maximum distance of an explosion from this entity.
     *
     * @return the maximum distance of an explosion from this entity.
     */
    override val maxExplosionDistance: Int
        get() = 10

    private val shootingChance: Int
        /**
         * Returns the chance of this entity shooting a projectile.
         *
         * @return the chance of this entity shooting a projectile.
         */
        get() = 1

    /**
     * Spawns orbs in all directions around this entity.
     */
    private fun spawnOrbs() {
        for (d in Direction.values()) {
            ClownNose(Coordinates.fromDirectionToCoordinateOnEntity(
                    this,
                    d,
                    Orb.SIZE,
                    Orb.SIZE
            ), d
            ).spawn(true, false)
        }
    }

    /**
     * Spawns enhanced orbs in all enhanced directions around this entity.
     */
    private fun spawnEnhancedOrbs() {
        for (d in EnhancedDirection.values()) {
            ClownNose(Coordinates.fromDirectionToCoordinateOnEntity(
                    this,
                    d,
                    Orb.SIZE
            ), d).spawn(true, false)
        }
    }

    /**
     * Calculates the explosion offsets based on the given direction.
     *
     * @param d the direction
     * @return the explosion offsets as an int array
     */
    private fun calculateExplosionOffsets(d: Direction): IntArray {
        var inwardOffset = size / 4
        var parallelOffset = -AbstractExplosion.SIZE / 2
        when (d) {
            Direction.RIGHT, Direction.LEFT -> {
                parallelOffset = 0
                inwardOffset = size / 3 - PitchPanel.GRID_SIZE / 2
            }

            else -> {}
        }
        return intArrayOf(inwardOffset, parallelOffset)
    }

    /**
     * Spawns an explosion in a random direction.
     */
    private fun spawnExplosion() {
        val dirs = Direction.values()
        val directions = LinkedList(listOf(*dirs))
        directions.remove(Direction.DOWN)

        val d = directions[(Math.random() * directions.size).toInt()]
        val offsets = calculateExplosionOffsets(d)

        AudioManager.getInstance().play(SoundModel.EXPLOSION_CONFETTI)

        val explosionCoordinates = Coordinates.fromDirectionToCoordinateOnEntity(
                this,
                d,
                offsets[0],
                offsets[1],
                AbstractExplosion.SIZE
        )

        ConfettiExplosion(
                this,
                explosionCoordinates,
                d,
                this
        )
    }

    /**
     * Throws a hat in a random enhanced direction.
     */
    private fun throwHat() {
        val direction = EnhancedDirection.randomDirectionTowardsCenter(this)
        val coordinates = Coordinates.fromDirectionToCoordinateOnEntity(this, direction, 0)
        val hat: Entity = Hat(coordinates, direction)

        hat.spawn(true, false)
        hasHat = false
    }

    /**
     * Updates this entity's state.
     *
     * @param gameState the current gamestate
     */
    /**
     * Overrides the base method to perform entity-specific updates, including potential attacks.
     *
     * @param gameState The current state of the game.
     */
    override fun doUpdate(gameState: Boolean) {
        // Check and potentially spawn explosions
        checkAndSpawnExplosion()

        // Check and potentially spawn orbs
        checkAndSpawnOrbs()

        // Check and potentially throw a hat
        checkAndThrowHat()

        // Perform the common update logic for the clown boss
        super.doUpdate(gameState)
    }

    /**
     * Checks if the clown boss should spawn an explosion and does so if the conditions are met.
     */
    private fun checkAndSpawnExplosion() {
        if (Utility.timePassed(lastAttackTime) <= attackDelay) return
        // Run a percentage-based chance for spawning an explosion
        Utility.runPercentage(shootingChance) {
            lastAttackTime = System.currentTimeMillis()
            spawnExplosion()
        }
    }

    /**
     * Checks if the clown boss should spawn orbs and does so if the conditions are met.
     */
    private fun checkAndSpawnOrbs() {
        if (Utility.timePassed(lastAttackTime) <= attackDelay) return
        // Run a percentage-based chance for spawning enhanced and regular orbs
        Utility.runPercentage(shootingChance) {
            lastAttackTime = System.currentTimeMillis()
            spawnEnhancedOrbs()
            spawnOrbs()
        }
    }

    /**
     * Checks if the clown boss should throw a hat and does so if the conditions are met.
     */
    private fun checkAndThrowHat() {
        if (Utility.timePassed(lastAttackTime) <= attackDelay || !hasHat) return
        // Run a percentage-based chance for throwing a hat
        Utility.runPercentage(shootingChance) {
            lastAttackTime = System.currentTimeMillis()
            throwHat()
        }
    }

    /**
     * Handles the Boss getting hit by the player, updating its rage status if necessary.
     *
     * @param damage the amount of damage the Boss received.
     */
    override fun onHit(damage: Int) {
        // Get the current health percentage of the Boss.
        val hpPercentage = hpPercentage
        // Get the entry from the health status map whose key is the lowest greater than or equal to hpPercentage.
        val entry = healthStatusMap.ceilingEntry(hpPercentage) ?: return

        // If there is an entry, update the rage status of the Boss.
        updateRageStatus(entry.value)
    }

    override val type: EntityTypes
        get() = EntityTypes.Clown

    companion object {
        private const val RATIO_HEIGHT_WITH_HAT = 0.7517f
        private const val RATIO_HEIGHT = 0.87f
        private const val RATIO_WIDTH = 0.8739f
        private const val SKIN_PATH_TEMPLATE = "%s/clown/clown_%s_%s.png"
    }
}
