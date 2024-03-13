package game.domain.world.domain.entity.actors.impl.placeable.bomb.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.actors.impl.placeable.base.state.PlaceableEntityState
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.presentation.ui.panels.game.PitchPanel
import java.util.concurrent.atomic.AtomicReference

class BombState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = Bomb.DEFAULT.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE
) : PlaceableEntityState(entity, isSpawned, isImmune, state, isInvisible, size, alpha, interactionEntities, lastImageUpdate) 