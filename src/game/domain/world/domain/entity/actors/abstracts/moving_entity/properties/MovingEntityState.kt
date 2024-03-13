package game.domain.world.domain.entity.actors.abstracts.moving_entity.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.state.EntityInteractableState
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction
import java.util.concurrent.atomic.AtomicReference

open class MovingEntityState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        obstacles: Set<Class<out Entity>> = EntityInteractable.DEFAULT.OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE,
        var direction: Direction = MovingEntity.DEFAULT.DIRECTION
) : EntityInteractableState(entity = entity,
        isSpawned = isSpawned,
        isImmune = isImmune,
        state = state,
        isInvisible = isInvisible,
        size = size,
        alpha = alpha,
        interactionEntities = interactionEntities,
        whitelistObstacles = whitelistObstacles,
        obstacles = obstacles,
        lastInteractionTime = lastInteractionTime,
        lastDamageTime = lastDamageTime,
        attackDamage = attackDamage
)