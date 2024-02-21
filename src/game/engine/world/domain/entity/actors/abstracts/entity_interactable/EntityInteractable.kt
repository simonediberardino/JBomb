package game.engine.world.domain.entity.actors.abstracts.entity_interactable

import game.engine.world.domain.entity.actors.abstracts.entity_interactable.logic.IEntityInteractableLogic
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.state.EntityInteractableState
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.geo.Coordinates

abstract class EntityInteractable : Entity {
    abstract override val logic: IEntityInteractableLogic
    abstract override val state: EntityInteractableState

    /**
     * Constructs an interactive entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor(id: Long) : super(id)
    constructor() : super()

    companion object {
        const val SHOW_DEATH_PAGE_DELAY_MS: Long = 2500
        const val INTERACTION_DELAY_MS: Long = 500
    }
}
