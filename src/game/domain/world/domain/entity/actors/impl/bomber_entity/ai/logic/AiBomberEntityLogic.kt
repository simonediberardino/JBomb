package game.domain.world.domain.entity.actors.impl.bomber_entity.ai.logic

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb.Companion.PLACE_INTERVAL
import game.domain.world.domain.entity.actors.impl.bomber_entity.ai.AiBomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.items.PistolItem
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.presentation.ui.panels.game.PitchPanel.GRID_SIZE
import game.utils.Utility
import game.utils.time.now
import kotlinx.coroutines.launch

class AiBomberEntityLogic(override val entity: AiBomberEntity) : AiLogic(entity = entity) {
    private var lastScanShootTime = 0L
    private var lastFireTime = 0L
    private var lastScanEnemyTime = 0L

    override fun onSpawn() {
        super.onSpawn()
        // Give the current entity a BombItem when it is spawned in the match.
        JBomb.match.currentLevel.info.defaultWeapons?.forEach {
            JBomb.match.give(entity, it)
        }

        entity.state.activePowerUpsInstances.forEach {
            it.logic.cancel(entity)
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

    private fun getClosestEnemyOrObjectiveInRange(): EntityInteractable? {
        val players = ArrayList(JBomb.match.players)

        // Find the closest player within range
        val closestPlayer = players
            .asSequence()
            .filter { it != entity } // Exclude the current entity
            .map { it to it.info.position.distanceTo(entity.info.position) } // Pair player with distance
            .filter { (_, distance) -> distance < GRID_SIZE * 5 } // Filter by range
            .minByOrNull { (_, distance) -> distance } // Find the closest player

        if (closestPlayer != null) {
            return closestPlayer.first // Return the player if found
        }

        val entities = ArrayList(JBomb.match.getEntities())

        // If no players are found, look for the closest power-up
        val closestPowerUp = entities
            .filterIsInstance<PowerUp>() // Only consider PowerUp entities
            .map { it to it.info.position.distanceTo(entity.info.position) } // Pair PowerUp with distance
            .filter { (_, distance) -> distance < GRID_SIZE * 5 } // Filter by range
            .minByOrNull { (_, distance) -> distance } // Find the closest power-up

        return closestPowerUp?.first // Return the power-up if found
    }

    // TODO
    // Replace it with dijkstra
    private fun targetClosestEnemy() {
        if (Utility.timePassed(lastScanEnemyTime) > 1200) {
            lastScanEnemyTime = now()
            val closestPlayerInRange = getClosestEnemyOrObjectiveInRange()
            destination = closestPlayerInRange?.info?.position
        }
    }

    private fun processShoot(): Boolean {
        if (!isReadyToShoot()) return false
        lastScanShootTime = now()

        val currentPos = Coordinates.roundCoordinates(entity.info.position)
        val players = ArrayList(JBomb.match.players)

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
        if (Utility.timePassed(lastFireTime) < PLACE_INTERVAL * 2) return false
        return Utility.timePassed(lastScanShootTime) >= 500
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
        JBomb.match.scope.launch {
            targetClosestEnemy()
            val hasShot = processShoot()
            if (!hasShot)
                super.process()
        }
    }

}