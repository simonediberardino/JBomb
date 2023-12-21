package game.entity.enemies.boss.clown

import game.entity.EntityTypes
import game.entity.Player
import game.entity.enemies.npcs.Orb
import game.entity.models.Coordinates
import game.entity.models.EnhancedDirection
import game.entity.models.Entity
import game.utils.Paths
import game.values.DrawPriority
import java.util.*

open class Hat(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : Orb(coordinates, enhancedDirection) {
    init {
        maxHp = 300
    }

    constructor() : this(null, null)

    override fun getEntitiesAssetsPath(): String = "${Paths.enemiesFolder}/clown/hat"
    override fun getDrawPriority(): DrawPriority = DrawPriority.DRAW_PRIORITY_3
    override fun getType(): EntityTypes = EntityTypes.Hat

    override fun getInteractionsEntities(): Set<Class<out Entity>> = hashSetOf(Player::class.java, Clown::class.java)

    override fun getObstacles(): Set<Class<out Entity>> = HashSet()

    override fun getCharacterOrientedImages(): Array<String> = Array(10) { index ->
        "${entitiesAssetsPath}${index + 1}.png"
    }

    override fun getSize(): Int = SIZE * 3

    override fun doInteract(e: Entity?) {
        if (e == null) return

        if (e is Clown) {
            e.setHasHat(true)
            despawn()
        } else {
            attack(e)
        }
    }

    override fun useOnlyBaseIcons(): Boolean = true

    private fun updateDirection() {
        if (!canMove || !isAlive)
            return

        if (enhancedDirection == null) {
            // When hitting a wall, bounce and change direction;
            if (!moveOrInteract(direction))
                direction = direction!!.opposite()

            updateLastDirection(direction)
            return
        }

        for (d in enhancedDirection!!.toDirection()) {
            currDirection = d
            if (!moveOrInteract(d)) {
                enhancedDirection = enhancedDirection!!.opposite(d)
            }
            updateLastDirection(currDirection)
        }
    }

    override fun doUpdate(gameState: Boolean) {
        if (gameState) updateDirection()
    }
}
