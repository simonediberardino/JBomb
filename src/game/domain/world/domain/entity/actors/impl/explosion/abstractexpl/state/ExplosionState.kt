package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityState
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.models.Explosive
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction
import game.utils.Utility
import game.utils.time.now
import kotlin.math.max

class ExplosionState(
        entity: AbstractExplosion,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = AbstractExplosion.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE,
        direction: Direction = MovingEntity.DEFAULT.DIRECTION,
        var owner: Entity,
        var explosive: Explosive,
        var distanceFromExplosive: Int = AbstractExplosion.DEFAULT.DISTANCE_FROM_EXPLOSIVE,
        var canExpand: Boolean = AbstractExplosion.DEFAULT.CAN_EXPAND,
        var blockedDirections : MutableList<Direction> = mutableListOf(),
        var isCenterVisible: Boolean = AbstractExplosion.DEFAULT.CENTER_VISIBLE
) : MovingEntityState(
        entity = entity,
        isSpawned = isSpawned,
        isImmune = isImmune,
        state = state,
        isInvisible = isInvisible,
        size = size,
        alpha = alpha,
        interactionEntities = interactionEntities,
        whitelistObstacles = whitelistObstacles,
        lastInteractionTime = lastInteractionTime,
        lastDamageTime = lastDamageTime,
        attackDamage = attackDamage,
        direction = direction
) {
    override val obstacles: Set<Class<out Entity>>
        get() = explosive.explosionObstacles

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = explosive.explosionInteractionEntities.toMutableSet()

    private var explosionState = -1
    private var appearing = true
    private var lastRefresh: Long = 0

    val _state: Int
        get() {
            if (explosionState == 0 && !appearing) {
                entity.logic.eliminated()
                appearing = true
                return 0
            }

            if (explosionState == AbstractExplosion.BOMB_STATES)
                appearing = false

            val appearingConstant = if (!appearing) -1 else 1
            val prevState = explosionState

            if (Utility.timePassed(lastRefresh) >= entity.image.imageRefreshRate) {
                explosionState += appearingConstant
                lastRefresh = now()
            }

            return max(0, prevState)
        }
}