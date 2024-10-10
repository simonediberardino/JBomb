package game.domain.world.domain.entity.actors.abstracts.entity_interactable.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityState
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.models.State

open class EntityInteractableState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        open val whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        open val obstacles: Set<Class<out Entity>> = EntityInteractable.DEFAULT.OBSTACLES,
        open var lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        open var lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        open var attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE
) : EntityState(
        entity = entity,
        isSpawned = isSpawned,
        isImmune = isImmune,
        state = state,
        isInvisible = isInvisible,
        size = size,
        alpha = alpha,
        interactionEntities = interactionEntities
) {
    var previousObserverUpdate: Long = 0L
}
