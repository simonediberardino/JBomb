package game.engine.world.entity.impl.enemies.boss.clown

import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.player.Player
import game.engine.world.entity.impl.enemies.npcs.Orb
import game.engine.world.geo.Coordinates
import game.engine.world.geo.EnhancedDirection
import game.engine.world.entity.impl.models.Entity
import game.utils.Paths
import game.values.DrawPriority
import java.util.*

open class Hat : Orb {
    constructor(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : super(coordinates, enhancedDirection) {
        initHat()
    }

    constructor() : this(null, null)

    constructor(id: Long) : super(id)

    private fun initHat() {
        maxHp = 300
    }

    override val entitiesAssetsPath: String get() ="${Paths.enemiesFolder}/clown/hat"
    override val drawPriority: DrawPriority
        get() = DrawPriority.DRAW_PRIORITY_3

    override val type: EntityTypes
        get() = EntityTypes.Hat

    override fun getInteractionsEntities(): Set<Class<out Entity>> = hashSetOf(Player::class.java, Clown::class.java)

    override fun getObstacles(): Set<Class<out Entity>> = HashSet()

    override fun getCharacterOrientedImages(): Array<String> = Array(10) { index ->
        "${entitiesAssetsPath}${index + 1}.png"
    }

    override val size: Int
        get() = SIZE * 3

    override fun doInteract(e: Entity?) {
        if (e == null) return

        if (e is Clown) {
            e.hasHat = true
            despawnAndNotify()
        } else {
            attack(e)
        }
    }

    private fun updateDirection() {
        if (!canMove || !isAlive)
            return

        if (enhancedDirection == null) {
            // When hitting a wall, bounce and change direction;
            if (!moveOrInteract(direction))
                direction = direction!!.opposite()

            updateMovementDirection(direction)
            return
        }

        for (d in enhancedDirection!!.toDirection()) {
            currDirection = d
            if (!moveOrInteract(d)) {
                enhancedDirection = enhancedDirection!!.opposite(d)
            }
            updateMovementDirection(currDirection)
        }
    }

    override fun doUpdate(gameState: Boolean) {
        if (gameState) updateDirection()
    }
}
