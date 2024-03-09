package game.domain.world.domain.entity.actors.abstracts.moving_entity.logic

import game.domain.world.domain.entity.actors.abstracts.entity_interactable.logic.IEntityInteractableLogic
import game.domain.world.domain.entity.geo.Direction

interface IMovingEntityLogic : IEntityInteractableLogic {
    fun availableDirections(): List<Direction>
}