package game.domain.world.domain.entity.actors.impl.bomber_entity.ai.logic

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb.Companion.PLACE_INTERVAL
import game.domain.world.domain.entity.actors.impl.bomber_entity.ai.AiBomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.items.PistolItem
import game.presentation.ui.panels.game.PitchPanel
import game.presentation.ui.panels.game.PitchPanel.GRID_SIZE
import game.utils.Utility
import game.utils.time.now

class AiBomberEntityLogic(override val entity: AiBomberEntity) : AiLogic(entity = entity) {
    private var lastScanShootTime = 0L
    private var lastFireTime = 0L

    override fun onSpawn() {
        super.onSpawn()
        // Give the current entity a BombItem when it is spawned in the match.
        JBomb.match.currentLevel.info.defaultWeapons?.forEach {
            JBomb.match.give(entity, it)
        }
    }

    override fun onAdded() {
        super.onAdded()
        JBomb.match.players.add(entity)
    }

    override fun onRemoved() {
        super.onRemoved()
        JBomb.match.players.removeIf { e -> e.info.id == entity.info.id }
    }

    private fun processShoot(): Boolean {
        if (!isReadyToShoot()) return false
        lastScanShootTime = now()

        val currentPos = Coordinates.roundCoordinates(entity.info.position)
        val players = JBomb.match.players

        val foundTarget = players.any { player ->
            if (player == entity) return@any false
            val targetPos = Coordinates.roundCoordinates(player.info.position)
            checkAndShootAtTarget(currentPos, targetPos)
        }

        return foundTarget
    }

    // Checks if the entity is in a state to shoot (spawned, has weapons, and ready based on time)
    private fun isReadyToShoot(): Boolean {
        if (!entity.state.isSpawned) return false
        if (entity.state.weapons.isEmpty()) return false
        if (Utility.timePassed(lastFireTime) < PLACE_INTERVAL) return false
        return Utility.timePassed(lastScanShootTime) >= 400
    }

    // Checks if the target is in line and within shooting range, then moves and fires in the correct direction
    private fun checkAndShootAtTarget(currentPos: Coordinates, targetPos: Coordinates): Boolean {
        val minDistance = PistolItem.explosionSize * GRID_SIZE

        return when {
            // Aligned vertically
            targetPos.x == currentPos.x -> checkAndShootVertical(currentPos, targetPos, minDistance)
            // Aligned horizontally
            targetPos.y == currentPos.y -> checkAndShootHorizontal(currentPos, targetPos, minDistance)
            // Not aligned for shooting
            else -> false
        }
    }

    // Checks vertical alignment and shoots if target is within range
    private fun checkAndShootVertical(currentPos: Coordinates, targetPos: Coordinates, minDistance: Int): Boolean {
        val diffY = targetPos.y - currentPos.y
        return when {
            diffY in 0 until minDistance -> {
                moveAndFire(Direction.DOWN)
                true
            }
            diffY in -minDistance..0 -> {
                moveAndFire(Direction.UP)
                true
            }
            else -> false
        }
    }

    // Checks horizontal alignment and shoots if target is within range
    private fun checkAndShootHorizontal(currentPos: Coordinates, targetPos: Coordinates, minDistance: Int): Boolean {
        val diffX = targetPos.x - currentPos.x
        return when {
            diffX in 0 until minDistance -> {
                moveAndFire(Direction.RIGHT)
                true
            }
            diffX in -minDistance..0 -> {
                moveAndFire(Direction.LEFT)
                true
            }
            else -> false
        }
    }

    // Moves in the specified direction and fires
    private fun moveAndFire(direction: Direction) {
        move(direction)
        fireWeapon()
    }

    // Fires the weapon and updates the last fire time
    private fun fireWeapon() {
        JBomb.match.useItem(entity)
        lastFireTime = now()
    }

    override fun process() {
        val hasShot = processShoot()
        if (!hasShot) super.process()
    }

}