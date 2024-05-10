package game.domain.world.domain.entity.actors.abstracts.entity_interactable.logic

import game.domain.level.behavior.GameBehavior
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.logic.EntityLogic
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Coordinates.getNewCoordinatesListOnDirection
import game.domain.world.domain.entity.geo.Direction
import game.network.events.forward.AttackEntityEventForwarder
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility.timePassed
import game.utils.time.now

abstract class EntityInteractableLogic(
        override val entity: EntityInteractable
) : EntityLogic(entity), IEntityInteractableLogic {
    override fun attack(e: Entity?) {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit {
                return {
                    if (!(e == null || e.state.isImmune || e.state.state == State.DIED)) {
                        val attackDamage: Int = entity.state.attackDamage
                        e.logic.onAttackReceived(attackDamage)
                        AttackEntityEventForwarder().invoke(e.toEntityNetwork(), attackDamage)
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
        entity.logic.unCollideAll()
        onMove(coordinates)
    }

    final override fun interact(e: Entity?) {
        if (e == null) {
            interactAndUpdateLastInteract(null)
            return
        }

        /*if (canInteractWith(e) && e.logic.canBeInteractedBy(entity)) {
            interactAndUpdateLastInteract(e)
        } else if (e is EntityInteractable && e.logic.canInteractWith(entity) && canBeInteractedBy(e)) {
            e.logic.interactAndUpdateLastInteract(entity)
        }*/

        // SUPER TODO CHECK THIS!
        if (!canInteractWith(e) || !e.logic.canBeInteractedBy(entity)) return

        entity.logic.interactAndUpdateLastInteract(e)

        if (e is EntityInteractable) {
            e.logic.interactAndUpdateLastInteract(entity)
        }
    }

    @Synchronized
    override fun interactAndUpdateLastInteract(e: Entity?) {
        if (timePassed(entity.state.lastInteractionTime) < EntityInteractable.INTERACTION_DELAY_MS) {
            return
        }
        doInteract(e) // Interact with the entity.
        if (e is EntityInteractable) {
            updateLastInteract(e) // Update the last interaction for this entity.
        }
    }

    private fun updateLastInteract(e: EntityInteractable) {
        e.state.lastInteractionTime = now()
    }


    /**
     * Moves or interacts with other entities in the given direction and with the default step size and offset.
     *
     * @param direction the direction to move or interact in
     * @return true if the entity can move in the given direction, false otherwise
     */
    override fun moveOrInteract(direction: Direction, stepSize: Int): Boolean {
        return moveOrInteract(direction, stepSize, false)
    }

    /**
     * Moves or interacts with other entities in the given direction and with the given step size and default offset.
     *
     * @param direction        the direction to move or interact in
     * @param stepSize the step size to use
     */
    override fun moveOrInteract(direction: Direction, stepSize: Int, ignoreMapBorders: Boolean): Boolean {
        val nextTopLeftCoords = Coordinates.nextCoords(
                entity.info.position,
                direction,
                stepSize
        )

        if (!nextTopLeftCoords.validate(entity) && !ignoreMapBorders) {
            interact(null)
            return false
        } else {
            /*val coordinatesInArea = Coordinates.getAllBlocksInAreaFromDirection(
                    entity,
                    direction,
                    PitchPanel.PIXEL_UNIT
            )

            // Check if all entities in the given area can be interacted with
            val allEntitiesCanBeInteractedWith = coordinatesInArea.all { coordinates: Coordinates? ->
                // Get entities on the current block
                val entitiesOnBlock = Coordinates.getEntitiesOnBlock(coordinates)

                // Check if there are no entities on the block or if all entities on the block can't be interacted with
                entitiesOnBlock.isEmpty() || entitiesOnBlock.all { entity: Entity ->
                    // Check if the current entity's logic prevents interaction, or if interaction with the entity is not allowed,
                    // or if the entity is an obstacle and does not have the same ID as the main entity
                    !entity.logic.canBeInteractedBy(entity) && !canInteractWith(entity) && !(isObstacle(entity) && entity.info.id != entity.info.id)
                }
            }


            if (allEntitiesCanBeInteractedWith) {
                move(nextTopLeftCoords)
                return true
            }*/
        }

        // Get the coordinates of the next positions that will be occupied if
        // the entity moves in a certain direction with a given step size
        val nextOccupiedCoords = getNewCoordinatesListOnDirection(
                entity.info.position,
                direction,
                stepSize,
                PitchPanel.GRID_SIZE / 3 / 2,
                entity.state.size
        )

        // Get a list of entities that are present in the next occupied coordinates
        val collidedEntities = Coordinates.getEntitiesOnCoordinates(
                nextOccupiedCoords
        )

        /*        try {
                    entity.state.collidedEntities.toTypedArray().forEach { e ->
                        if (!collidedEntities.contains(e)) {
                            unCollide(e)
                        }
                    }
                } catch (_: Exception) {}*/

        val isObstacleOrCanInteract = collidedEntities.any {
            entity.logic.isObstacle(it) || entity.logic.canBeInteractedBy(it) || canInteractWith(it)
        }

        // move only if there is not a interactable or obstacle entity in the next position
        if (!isObstacleOrCanInteract) {
            move(nextTopLeftCoords)
            return true
        }

        collidedEntities.parallelStream()
                .forEach {
                    val canInteract = entity.logic.canBeInteractedBy(it) || canInteractWith(it)

                    // if can interact or the given entity is an obstacle, collide with it
                    if (canInteract || isObstacle(it))
                        collide(it)

                    // if can interact, interacft
                    if (canInteract) {
                        interact(it)
                    }
                }

        // Return whether the entity can move or not
        return false
    }

    override fun isObstacle(e: Entity?): Boolean {
        if (e == null)
            return true

        if (entity.state.whitelistObstacles.any { c -> c.isInstance(e) })
            return false

        return entity.state.obstacles.any { c -> c.isInstance(e) }
    }

    override fun canInteractWith(e: Entity?): Boolean =
            e == null || entity.state.interactionEntities.any { c -> c.isInstance(e) }
}