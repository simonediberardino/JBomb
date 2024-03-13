package game.domain.world.domain.entity.pickups.powerups.base.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.state.EntityInteractableState
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import java.util.concurrent.atomic.AtomicReference

open class PowerUpState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = PowerUp.DEFAULT.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = PowerUp.DEFAULT.INTERACTION_ENTITIES,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        obstacles: Set<Class<out Entity>> = PowerUp.DEFAULT.OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE
) : EntityInteractableState(
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
        attackDamage = attackDamage
) {
    open val duration: Int = PowerUp.DEFAULT.DURATION_SEC
    open val isDisplayable: Boolean = PowerUp.DEFAULT.IS_DISPLAYABLE

    // Whether the power-up has already been applied or not
    var applied = false
    lateinit var bomberEntity: BomberEntity
    open val incompatiblePowerUps = PowerUp.DEFAULT.INCOMPATIBLE_POWER_UPS
}