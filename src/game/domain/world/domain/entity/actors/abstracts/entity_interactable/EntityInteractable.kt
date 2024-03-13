package game.domain.world.domain.entity.actors.abstracts.entity_interactable

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.logic.IEntityInteractableLogic
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.state.EntityInteractableState
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates

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

    internal object DEFAULT {
        val WHITELIST_OBSTACLES: MutableSet<Class<out Entity>> = hashSetOf()
        val OBSTACLES: MutableSet<Class<out Entity>> = mutableSetOf(Bomb::class.java, Character::class.java, Block::class.java)
        val LAST_INTERACTION_TIME: Long = 0L
        val LAST_DAMAGE_TIME: Long = 0L
        val ATTACK_DAMAGE = 100
    }
}