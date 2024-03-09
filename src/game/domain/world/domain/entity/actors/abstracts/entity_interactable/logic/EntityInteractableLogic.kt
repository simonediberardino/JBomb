package game.domain.world.domain.entity.actors.abstracts.entity_interactable.logic

import game.domain.level.behavior.GameBehavior
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.logic.EntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.network.events.forward.AttackEntityEventForwarder
import game.utils.Utility.timePassed
import java.util.*

abstract class EntityInteractableLogic(
        override val entity: EntityInteractable
) : EntityLogic(entity), IEntityInteractableLogic {
    override fun attack(e: Entity?) {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit {
                return {
                    if (!(e == null || e.state.isImmune)) {
                        val attackDamage: Int = entity.state.attackDamage
                        e.logic.onAttackReceived(attackDamage)
                        AttackEntityEventForwarder().invoke(e.toDto(), attackDamage)
                    }
                }
            }

            override fun clientBehavior(): () -> Unit {
                return {}
            }

        }
        gameBehavior.invoke()
    }

    override fun move(coordinates: Coordinates) {
        entity.info.position = coordinates
        onMove(coordinates)
    }

    override fun interact(e: Entity?) {
        if (e == null) {
            interactAndUpdateLastInteract(null)
            return
        }

        if (canInteractWith(e) && e.logic.canBeInteractedBy(entity)) {
            interactAndUpdateLastInteract(e)
        } else if (e is EntityInteractable && e.logic.canInteractWith(entity) && entity.logic.canBeInteractedBy(e)) {
            // TODO Changed
            e.logic.interact(entity)
        }
    }

    @Synchronized
    fun interactAndUpdateLastInteract(e: Entity?) {
        if (timePassed(entity.state.lastInteractionTime) < EntityInteractable.INTERACTION_DELAY_MS) {
            return
        }
        doInteract(e) // Interact with the entity.
        updateLastInteract(e) // Update the last interaction for this entity.
    }

    private fun updateLastInteract(e: Entity?) {
        if (e == null) return
        entity.state.lastInteractionTime = now()
    }


    /**
     * Moves or interacts with other entities in the given direction and with the default step size and offset.
     *
     * @param d the direction to move or interact in
     * @return true if the entity can move in the given direction, false otherwise
     */
    override fun moveOrInteract(d: Direction?, stepSize: Int): Boolean {
        return moveOrInteract(d, stepSize, false)
    }

    /**
     * Moves or interacts with other entities in the given direction and with the given step size and default offset.
     *
     * @param d        the direction to move or interact in
     * @param stepSize the step size to use
     */
    override fun moveOrInteract(d: Direction?, stepSize: Int, ignoreMapBorders: Boolean): Boolean {
        if (d == null) return false

        val nextTopLeftCoords = Coordinates.nextCoords(entity.info.position, d, stepSize)

        if (!nextTopLeftCoords.validate(entity)) {
            if (!ignoreMapBorders) {
                interact(null)
                return false
            }
        } else {
            val coordinatesInArea = Coordinates.getAllBlocksInAreaFromDirection(
                    entity,
                    d,
                    stepSize
            )

            val allEntitiesCanBeInteractedWith = coordinatesInArea.all {
                c: Coordinates? -> Coordinates.getEntitiesOnBlock(c).none { e: Entity ->
                    entity.logic.canBeInteractedBy(e) || canInteractWith(e) || isObstacle(e) && e !== entity
                }
            }

            if (allEntitiesCanBeInteractedWith) {
                move(nextTopLeftCoords)
                return true
            }
        }

        // Get the coordinates of the next positions that will be occupied if the entity moves in a certain direction
        // with a given step size
        val nextOccupiedCoords = Coordinates.getNewCoordinatesOnDirection(entity.info.position, d, stepSize, PitchPanel.GRID_SIZE / 3 / 2, entity.state.size)

        // Get a list of entities that are present in the next occupied coordinates
        val interactedEntities = Coordinates.getEntitiesOnCoordinates(nextOccupiedCoords)

        // If there are no entities present in the next occupied coordinates, update the entity's position
        if (interactedEntities.isEmpty()) {
            move(nextTopLeftCoords)
            return true
        }

        // Initialize a flag to indicate whether the entity can move
        var canMove = true

        // Check if any of the entities in the 'interactedEntities' list is an obstacle
        if (interactedEntities.stream().anyMatch { e: Entity? -> isObstacle(e) }) {
            // Filter and collect the obstacle entities into a temporary list
            val obstacleEntities = interactedEntities.filter { e: Entity? -> isObstacle(e) }

            // Interact with each obstacle entity in the temporary list
            for (e in obstacleEntities) {
                interact(e)
            }
            canMove = false
        } else {
            // Interact with non-null entities in the 'interactedEntities' list
            interactedEntities.stream().filter { obj: Entity? -> Objects.nonNull(obj) }.forEach { e: Entity? -> interact(e) }
        }

        // If the entity can move or it is immune to bombs, update the entity's position
        //if the entity is instance of explosion, it'll be able to move further anyway but no more explosions will be generated in constructor
        if (entity is AbstractExplosion && !canMove) {
            (entity as AbstractExplosion).logic.onObstacle(nextTopLeftCoords)
        } else if (canMove) {
            move(nextTopLeftCoords)
        }

        // Return whether the entity can move or not
        return canMove
    }

    override fun isObstacle(e: Entity?): Boolean {
        return e == null || entity.state.obstacles.any { c: Class<out Entity> -> c.isInstance(e) }
                && entity.state.whitelistObstacles.none { c: Class<out Entity> -> c.isInstance(e) }
    }

    override fun canInteractWith(e: Entity?): Boolean {
        return e == null || entity.state.interactionEntities.any { c: Class<out Entity?> -> c.isInstance(e) }
    }

}