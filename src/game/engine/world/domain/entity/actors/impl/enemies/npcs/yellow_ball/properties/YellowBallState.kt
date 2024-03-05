package game.engine.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.properties

import game.engine.hardwareinput.Command
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.actors.impl.models.State
import game.engine.world.domain.entity.geo.Direction
import java.util.concurrent.atomic.AtomicReference

class YellowBallState(
        entity: Entity,
        size: Int = Enemy.DEFAULT.SIZE,
        interactionEntities: MutableSet<Class<out Entity>> = Enemy.DEFAULT.INTERACTION_ENTITIES,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: AtomicReference<State>? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        obstacles: Set<Class<out Entity>> = EntityInteractable.DEFAULT.OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE,
        direction: Direction = MovingEntity.DEFAULT.DIRECTION,
        lastDirectionUpdate: Long = Character.DEFAULT.LAST_DIRECTION_UPDATE,
        commandQueue: MutableSet<Command> = Character.DEFAULT.COMMAND_QUEUE,
        previousDirection: Direction? = Character.DEFAULT.PREVIOUS_DIRECTION,
        canMove: Boolean = Character.DEFAULT.CAN_MOVE,
        maxHp: Int = Character.DEFAULT.MAX_HP,
        speed: Float = Character.DEFAULT.SPEED,
        imageDirection: Direction? = Character.DEFAULT.IMAGE_DIRECTION
) : EnemyEntityState(
        entity = entity,
        size = size,
        interactionEntities = interactionEntities,
        isSpawned = isSpawned,
        isImmune = isImmune,
        state = state,
        isInvisible = isInvisible,
        alpha = alpha,
        whitelistObstacles = whitelistObstacles,
        obstacles = obstacles,
        lastInteractionTime = lastInteractionTime,
        lastDamageTime = lastDamageTime,
        attackDamage = attackDamage,
        direction = direction,
        lastDirectionUpdate = lastDirectionUpdate,
        commandQueue = commandQueue,
        previousDirection = previousDirection,
        canMove = canMove,
        maxHp = maxHp,
        speed = speed,
        imageDirection = imageDirection
) {
}