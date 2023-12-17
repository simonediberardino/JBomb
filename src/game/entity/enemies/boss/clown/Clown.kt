package game.entity.enemies.boss.clown

import game.Bomberman
import game.entity.EntityTypes
import game.entity.Player
import game.entity.bomb.AbstractExplosion
import game.entity.bomb.Bomb
import game.entity.bomb.ConfettiExplosion
import game.entity.enemies.boss.Boss
import game.entity.enemies.npcs.ClownNose
import game.entity.enemies.npcs.Orb
import game.entity.models.*
import game.sound.AudioManager
import game.sound.SoundModel
import game.ui.panels.game.PitchPanel
import game.utils.Paths
import game.utils.Utility
import java.util.*

/**
 * The Clown class represents a type of Boss entity that implements the Explosive interface.
 * It has a boolean property hasHat that determines whether the Clown is wearing a hat or not.
 * The Clown entity can spawn orbs, enhanced orbs, explosions and throw its hat in random directions.
 */
class Clown : Boss, Explosive {
    private val explosions = ArrayList<ConfettiExplosion>()

    /**
     * The hasHat property represents whether the Clown entity is wearing a hat or not.
     */
    private var hasHat = false

    /**
     * Constructor for the Clown entity that takes in the entity's starting coordinates and sets its initial hasHat value to true.
     *
     * @param coordinates The starting coordinates of the Clown entity.
     */
    private constructor(coordinates: Coordinates) : super(coordinates)

    constructor() : super(null) {
        hitboxSizetoWidthRatio = RATIO_WIDTH
        hasHat = true

        val panelSize = Bomberman
                .getBombermanFrame()
                .pitchPanel
                .preferredSize

        val y = panelSize.getHeight().toInt() - size
        val x = (panelSize.getWidth() / 2 - size / 2).toInt()
        coords = Coordinates(x, y)
    }

    /**
     * Overrides the getBaseSkins method from the Boss superclass to return an array of skin paths based on the hasHat value.
     *
     * @return A String array of skin paths for the Clown entity.
     */
    override fun getCharacterOrientedImages(): Array<String> {
        return arrayOf(getImageFromRageStatus())
    }

    /**
     * @return A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    fun isHatImage(path: String): Boolean {
        val toks = path.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (toks.size <= 1) false else toks[1] == "1"
    }

    override fun getHitboxSizeToHeightRatio(path: String): Float {
        hitboxSizeToHeightRatio = if (isHatImage(path)) RATIO_HEIGHT_WITH_HAT else RATIO_HEIGHT
        return hitboxSizeToHeightRatio
    }

    override fun calculateAndGetPaddingTop(ratio: Double): Int {
        return super.calculateAndGetPaddingTop(ratio)
    }

    /**
     * Setter method for the hasHat property.
     *
     * @param hasHat A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    fun setHasHat(hasHat: Boolean) {
        this.hasHat = hasHat
    }

    /**
     * Overrides the getExplosionObstacles method from the Explosive interface to return an empty list.
     *
     * @return An empty List object.
     */
    override val explosionObstacles: Set<Class<out Entity>>
        get() {
            return emptySet()
        }

    /**
     * Returns a list of entity classes that can interact with explosions.
     *
     * @return a list of entity classes that can interact with explosions.
     */
    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() {
            return setOf(
                    Player::class.java,
                    Bomb::class.java
            )
        }

    /**
     * Returns the maximum distance of an explosion from this entity.
     *
     * @return the maximum distance of an explosion from this entity.
     */
    override val maxExplosionDistance: Int
        get() {
            return 10
        }

    private val shootingChance: Int
        /**
         * Returns the chance of this entity shooting a projectile.
         *
         * @return the chance of this entity shooting a projectile.
         */
        private get() = 1

    /**
     * Spawns orbs in all directions around this entity.
     */
    private fun spawnOrbs() {
        for (d in Direction.values()) {
            ClownNose(
                    Coordinates.fromDirectionToCoordinateOnEntity(
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
    fun throwHat() {
        val d = EnhancedDirection.randomDirectionTowardsCenter(this)
        val hat: Entity = Hat(
                Coordinates.fromDirectionToCoordinateOnEntity(
                        this,
                        d,
                        0),
                d
        )
        hat.spawn(true, false)
        hasHat = false
    }

    /**
     * Updates this entity's state.
     *
     * @param gamestate the current gamestate
     */
    override fun doUpdate(gamestate: Boolean) {
        // Check if the entity should shoot orbs
        Utility.runPercentage(shootingChance) {
            spawnEnhancedOrbs()
            spawnOrbs()
        }

        // Check if the entity should shoot an explosion
        Utility.runPercentage(shootingChance) { spawnExplosion() }
        if (hasHat) {
            Utility.runPercentage(shootingChance) { throwHat() }
        }
        super.doUpdate(gamestate)
    }

    /**
     * Returns the image path of the Boss corresponding to its current rage status.
     *
     * @return the image path of the Boss.
     */
    override fun getImageFromRageStatus(): String {
        // Format the skin path template with the appropriate values.
        return String.format(SKIN_PATH_TEMPLATE, Paths.enemiesFolder, if (hasHat) 1 else 0, currRageStatus)
    }

    /**
     * Returns a list with the supported directions of the Boss.
     *
     * @return the list with the supported directions.
     */
    override fun getSupportedDirections(): List<Direction> {
        return listOf(Direction.LEFT, Direction.RIGHT)
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
        val entry = healthStatusMap.ceilingEntry(hpPercentage)

        // If there is an entry, update the rage status of the Boss.
        if (entry != null) {
            updateRageStatus(entry.value)
        }
    }

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
    override fun getHitboxSizeToHeightRatio(): Float {
        // Set the height to hitbox size ratio based on whether the Boss has a hat or not.
        hitboxSizeToHeightRatio = if (hasHat) RATIO_HEIGHT_WITH_HAT else RATIO_HEIGHT
        return hitboxSizeToHeightRatio
    }

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> {
        val list: MutableList<Class<out Entity>> = ArrayList(super.getBasePassiveInteractionEntities())
        list.add(Hat::class.java)
        return HashSet(list)
    }

    override fun getType(): EntityTypes {
        return EntityTypes.Clown
    }

    companion object {
        private const val RATIO_HEIGHT_WITH_HAT = 0.7517f
        private const val RATIO_HEIGHT = 0.87f
        private const val RATIO_WIDTH = 0.8739f
        private const val SKIN_PATH_TEMPLATE = "%s/clown/clown_%s_%s.png"
    }
}
