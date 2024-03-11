package game.domain.world.domain.entity.actors.abstracts.character.properties

import game.input.Command
import game.domain.tasks.GameTickerObserver.Companion.DEFAULT_OBSERVER_UPDATE
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityState
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction
import java.util.concurrent.atomic.AtomicReference
import game.domain.world.domain.entity.actors.abstracts.character.Character as Character

abstract class CharacterEntityState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: AtomicReference<State>? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = Character.DEFAULT.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        obstacles: Set<Class<out Entity>> = EntityInteractable.DEFAULT.OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE,
        direction: Direction = MovingEntity.DEFAULT.DIRECTION,
        var lastDirectionUpdate: Long = Character.DEFAULT.LAST_DIRECTION_UPDATE,
        var previousDirection: Direction? = Character.DEFAULT.PREVIOUS_DIRECTION,
        var canMove: Boolean = Character.DEFAULT.CAN_MOVE,
        var maxHp: Int = Character.DEFAULT.MAX_HP,
        var speed: Float = Character.DEFAULT.SPEED
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
        obstacles = obstacles,
        lastInteractionTime = lastInteractionTime,
        lastDamageTime = lastDamageTime,
        attackDamage = attackDamage,
        direction = direction
) {
    val delayObserverUpdate = DEFAULT_OBSERVER_UPDATE / speed
    var hp: Int = maxHp
    val hpPercentage: Int
        get() = (hp.toFloat() / maxHp.toFloat() * 100).toInt()

    var imageDirection: Direction? = null
        get() {
            val supportedDirections = (entity as Character).properties.imageDirections

            return if (field == null || !supportedDirections.contains(field)) {
                field = supportedDirections.first()
                field
            } else field
        }
}