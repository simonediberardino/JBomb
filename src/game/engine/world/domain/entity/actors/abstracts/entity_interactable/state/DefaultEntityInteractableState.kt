package game.engine.world.domain.entity.actors.abstracts.entity_interactable.state

import game.engine.world.domain.entity.actors.impl.blocks.DestroyableBlock
import game.engine.world.domain.entity.actors.impl.blocks.HardBlock
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.models.State
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import java.util.concurrent.atomic.AtomicReference

class DefaultEntityInteractableState(
        isSpawned: Boolean,
        isImmune: Boolean,
        state: AtomicReference<State>?,
        isInvisible: Boolean,
        size: Int,
        alpha: Float,
        interactionEntities: MutableSet<Class<out Entity>>,
        whitelistObstacles: MutableSet<Class<out Entity>> = hashSetOf(
                HardBlock::class.java,
                Bomb::class.java,
                Enemy::class.java,
                DestroyableBlock::class.java,
                BomberEntity::class.java
        ),
        obstacles: Set<Class<out Entity>>,
        lastInteractionTime: Long,
        lastDamageTime: Long,
        attackDamage: Int
) : EntityInteractableState(
        isSpawned,
        isImmune,
        state,
        isInvisible,
        size,
        alpha,
        interactionEntities,
        whitelistObstacles,
        obstacles,
        lastInteractionTime,
        lastDamageTime,
        attackDamage
)