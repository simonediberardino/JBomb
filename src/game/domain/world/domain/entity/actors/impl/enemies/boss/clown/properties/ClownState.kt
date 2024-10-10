package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityState
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.domain.world.domain.entity.actors.abstracts.models.State
import game.domain.world.domain.entity.geo.Direction

class ClownState(
        entity: Entity,
        size: Int = Boss.DEFAULT.SIZE,
        interactionEntities: MutableSet<Class<out Entity>> = Clown.DEFAULT.INTERACTION_ENTITIES,
        speed: Float = Character.DEFAULT.SPEED,
        maxHp: Int = Character.DEFAULT.MAX_HP,
        currRageStatus: Int = Boss.DEFAULT.START_RAGE_STATUS,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        whitelistObstacles: MutableSet<Class<out Entity>> = EntityInteractable.DEFAULT.WHITELIST_OBSTACLES,
        obstacles: Set<Class<out Entity>> = Boss.DEFAULT.OBSTACLES,
        lastInteractionTime: Long = EntityInteractable.DEFAULT.LAST_INTERACTION_TIME,
        lastDamageTime: Long = EntityInteractable.DEFAULT.LAST_DAMAGE_TIME,
        attackDamage: Int = EntityInteractable.DEFAULT.ATTACK_DAMAGE,
        direction: Direction = MovingEntity.DEFAULT.DIRECTION,
        lastDirectionUpdate: Long = Character.DEFAULT.LAST_DIRECTION_UPDATE,
        
        previousDirection: Direction? = Character.DEFAULT.PREVIOUS_DIRECTION,
        canMove: Boolean = Character.DEFAULT.CAN_MOVE,
        
        var attackDelay: Int = Clown.DEFAULT.ATTACK_DELAY,
        var lastAttackTime: Long = Clown.DEFAULT.LAST_ATTACK_TIME,
        var hasHat: Boolean = Clown.DEFAULT.HAS_HAT
) : BossEntityState(
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
        
        previousDirection = previousDirection,
        canMove = canMove,
        maxHp = maxHp,
        speed = speed,
        currRageStatus = currRageStatus
) {
    internal var hatThrowTime: Long = 0L
    internal var hatPickupDelay: Long = 10_000L
}