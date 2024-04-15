package game.domain.world.domain.entity.actors.impl.bomber_entity.player.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityState
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction
import game.input.Command
import java.util.concurrent.atomic.AtomicReference

class PlayerState(
        entity: BomberEntity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = Character.DEFAULT.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = BomberEntity.DEFAULT.INTERACTION_ENTITIES,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        obstacles: Set<Class<out Entity>> = EntityInteractable.DEFAULT.OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE,
        direction: Direction = MovingEntity.DEFAULT.DIRECTION,
        lastDirectionUpdate: Long = Character.DEFAULT.LAST_DIRECTION_UPDATE,
        previousDirection: Direction? = Character.DEFAULT.PREVIOUS_DIRECTION,
        canMove: Boolean = Character.DEFAULT.CAN_MOVE,
        maxHp: Int = BomberEntity.DEFAULT.MAX_HP,
        speed: Float = Character.DEFAULT.SPEED
) : BomberEntityState(entity = entity, isSpawned = isSpawned, isImmune = isImmune, state = state, isInvisible = isInvisible, size = size, alpha = alpha, interactionEntities = interactionEntities, whitelistObstacles = whitelistObstacles, obstacles = obstacles, lastInteractionTime = lastInteractionTime, lastDamageTime = lastDamageTime, attackDamage = attackDamage, direction = direction, lastDirectionUpdate = lastDirectionUpdate, previousDirection = previousDirection, canMove = canMove, maxHp = maxHp, speed = speed) {
    val commandQueue: MutableSet<Command> = mutableSetOf()
}