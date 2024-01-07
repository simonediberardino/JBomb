package game.entity.enemies.npcs

import game.entity.player.Player
import game.entity.placeable.Bomb
import game.entity.models.*
import game.ui.panels.game.PitchPanel

/**
 * The Orb class represents a little enemy entity that moves in a specific direction.
 * It can be instantiated with either an EnhancedDirection or a Direction, but not both.
 * The Orb class implements the Transparent and Particle interfaces.
 */
abstract class Orb : Enemy {
    /**
     * Only one field between enhancedDirection and direction can be instantiated at a time.
     * The enhancedDirection represents a direction that has been enhanced with additional directions.
     */
    protected var enhancedDirection: EnhancedDirection? = null

    /**
     * The direction represents the basic direction that the Orb moves in.
     */
    protected var direction: Direction? = null

    /**
     * Constructs an Orb with the given coordinates and enhanced direction.
     *
     * @param coordinates       the coordinates of the Orb
     * @param enhancedDirection the enhanced direction of the Orb
     */
    constructor(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : super(coordinates) {
        this.enhancedDirection = enhancedDirection
    }

    /**
     * Constructs an Orb with the given coordinates and direction.
     *
     * @param coordinates the coordinates of the Orb
     * @param direction   the direction of the Orb
     */
    constructor(coordinates: Coordinates?, direction: Direction?) : super(coordinates) {
        this.direction = direction
    }

    override fun getSpeed(): Float = 1.5f

    override val size: Int
        get() = SIZE

    /**
     * Returns the set of interaction entities for the Orb.
     *
     * @return the set of interaction entities for the Orb
     */
    override fun getInteractionsEntities(): Set<Class<out Entity>> = hashSetOf(Player::class.java, Bomb::class.java)

    /**
     * Returns whether the given entity is an obstacle.
     *
     * @param e the entity to check
     * @return true if the entity is null, false otherwise
     */
    //
    override fun isObstacle(e: Entity?): Boolean = e == null

    /**
     * Performs an interaction with the given entity.
     *
     * @param e the entity to interact with
     */
    //
    override fun doInteract(e: Entity?) {
        if (canInteractWith(e)) {
            attack(e)
        }
        if (isObstacle(e)) {
            attack(this)
        }
    }

    /**
     * Moves the Orb in the appropriate direction or directions.
     */
    private fun moveOrb() {
        if (!canMove || !isAlive)
            return

        if (enhancedDirection == null) {
            move(direction)
            return
        }
        for (d in enhancedDirection!!.toDirection()) {
            moveOrInteract(d)
        }
    }

    override fun doUpdate(gameState: Boolean) {
        if (gameState) moveOrb()
    }

    companion object {
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
    }
}