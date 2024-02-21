package game.engine.world.domain.entity.actors.abstracts.character.properties

import game.engine.hardwareinput.Command
import game.engine.tasks.GameTickerObserver.Companion.DEFAULT_OBSERVER_UPDATE
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.state.EntityInteractableState
import game.engine.world.domain.entity.actors.impl.models.State
import game.engine.world.domain.entity.geo.Direction
import java.util.HashSet
import java.util.concurrent.atomic.AtomicReference

abstract class CharacterEntityState(
        isSpawned: Boolean = false,
        isImmune: Boolean = false,
        state: AtomicReference<State>? = AtomicReference(),
        isInvisible: Boolean = false,
        size: Int,
        alpha: Float = 1f,
        interactionEntities: MutableSet<Class<out Entity>> = mutableSetOf(),
        whitelistObstacles: MutableSet<Class<out Entity>> = mutableSetOf(),
        obstacles: Set<Class<out Entity>> = mutableSetOf(),
        lastInteractionTime: Long = 0L,
        lastDamageTime: Long = 0,
        attackDamage: Int = 100,
        var lastDirectionUpdate: Long = 0,
        var commandQueue: MutableSet<Command> = HashSet(),
        var currDirection: Direction? = Direction.DOWN,
        var previousDirection: Direction? = null,
        var canMove: Boolean = true,
        var maxHp: Int = 100,
        var hp: Int = maxHp,
        var speed: Float = 1f,
        var imageDirection: Direction? = null
) : EntityInteractableState(
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
    val delayObserverUpdate = DEFAULT_OBSERVER_UPDATE / speed
    val hpPercentage: Int
        get() = (hp.toFloat() / maxHp.toFloat() * 100).toInt()

}