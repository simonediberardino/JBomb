package game.entity.enemies.boss.clown

import game.entity.Player
import game.entity.enemies.boss.clown.Clown
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

    override fun getBasePath(): String {
        return "${Paths.getEnemiesFolder()}/clown/hat"
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return arrayOf(
                "${basePath}1.png",
                "${basePath}2.png",
                "${basePath}3.png",
                "${basePath}4.png",
                "${basePath}5.png",
                "${basePath}6.png",
                "${basePath}7.png",
                "${basePath}8.png",
                "${basePath}9.png",
                "${basePath}10.png")
    }

    override fun getSize(): Int {
        return SIZE * 3
    }

    override fun doInteract(e: Entity?) {
        if (e == null) return

        if (e is Clown) {
            e.setHasHat(true)
            despawn()
        } else {
            attack(e)
        }
    }

    override fun useOnlyBaseIcons(): Boolean {
        return true
    }

    private fun updateDirection() {
        if (!canMove || !isAlive)
            return

        if (enhancedDirection == null) {
            // When hitting a wall, bounce and change direction;
            if (!moveOrInteract(direction)) direction = direction.opposite()
            updateLastDirection(direction)
            return
        }

        for (d in enhancedDirection.toDirection()) {
            currDirection = d
            if (!moveOrInteract(d)) {
                enhancedDirection = enhancedDirection.opposite(d)
            }
            updateLastDirection(currDirection)
        }
    }

    override fun getInteractionsEntities(): Set<Class<out Entity>> {
        return HashSet<Class<out Entity>>(listOf(Player::class.java, Clown::class.java))
    }

    override fun getObstacles(): Set<Class<out Entity>> {
        return HashSet()
    }

    override fun doUpdate(gameState: Boolean) {
        if (gameState) updateDirection()
    }

    override fun getDrawPriority(): DrawPriority {
        return DrawPriority.DRAW_PRIORITY_3
    }
}
