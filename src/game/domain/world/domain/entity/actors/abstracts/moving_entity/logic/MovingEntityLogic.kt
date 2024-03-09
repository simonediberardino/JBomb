package game.domain.world.domain.entity.actors.abstracts.moving_entity.logic

import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.logic.EntityInteractableLogic
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import java.util.*

abstract class MovingEntityLogic(
        override val entity: EntityInteractable
) : EntityInteractableLogic(entity), IMovingEntityLogic {

    /**
     * Gets a list of available directions for the entity to move, based on the current game state.
     * A direction is considered available if moving in that direction would not result in a collision
     * with another entity or an invalid location.
     *
     * @return a list of available directions
     */
    override fun availableDirections(): List<Direction> {
        val result: MutableList<Direction> = LinkedList()

        // Iterate over each direction
        for (d in Direction.values()) {
            val areCoordinatesValid = Coordinates.getEntitiesOnBlock(
                    Coordinates.nextCoords(entity.info.position, d, entity.state.size)
            ).any { entity.logic.canInteractWith(it) }

            if (areCoordinatesValid) {
                result.add(d)
                return result
            }
        }

        for (d in Direction.values()) {
            val newCoordinates = Coordinates.getNewCoordinatesOnDirection(
                    entity.info.position,
                    d,
                    PitchPanel.PIXEL_UNIT,
                    entity.state.size / 2,
                    entity.state.size
            )

            // Check if any entities on the next coordinates are blocks or have invalid coordinates
            var areCoordinatesValid = Coordinates.getEntitiesOnCoordinates(
                    newCoordinates
            ).none { entity.logic.isObstacle(it) }

            areCoordinatesValid = areCoordinatesValid && newCoordinates[0].validate(entity) // since the pitch is squared, if a coordinate is valid, then all the other new ones are as well;

            // If all the next coordinates are valid, add this direction to the list of available directions
            if (areCoordinatesValid) {
                result.add(d)
            }
        }

        return result
    }

}