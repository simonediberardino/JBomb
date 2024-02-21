package game.engine.world.domain.entity.actors.abstracts.entity_interactable.state

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityState
import game.engine.world.domain.entity.actors.impl.models.State
import java.util.concurrent.atomic.AtomicReference

open class EntityInteractableState(isSpawned: Boolean = false,
                                   isImmune: Boolean = false,
                                   state: AtomicReference<State>? = AtomicReference(),
                                   isInvisible: Boolean = false,
                                   size: Int,
                                   alpha: Float = 1f,
                                   interactionEntities: MutableSet<Class<out Entity>> = mutableSetOf(),
                                   val whitelistObstacles: MutableSet<Class<out Entity>> = mutableSetOf(),
                                   val obstacles: Set<Class<out Entity>> = mutableSetOf(),
                                   var lastInteractionTime: Long = 0L,
                                   var lastDamageTime: Long = 0,
                                   var attackDamage: Int = 100
) : EntityState(isSpawned, isImmune, state, isInvisible, size, alpha, interactionEntities)
