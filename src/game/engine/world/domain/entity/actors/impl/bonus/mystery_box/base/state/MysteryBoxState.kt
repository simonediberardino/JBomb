package game.engine.world.domain.entity.actors.impl.bonus.mystery_box.base.state

import game.engine.level.levels.Level
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.engine.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.engine.world.domain.entity.actors.impl.models.State
import java.util.concurrent.atomic.AtomicReference

abstract class MysteryBoxState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: AtomicReference<State>? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = MysteryBox.DEFAULT.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE
) : BlockEntityState(entity = entity,
        isSpawned = isSpawned,
        isImmune = isImmune,
        state = state,
        isInvisible = isInvisible,
        size = size,
        alpha = alpha,
        interactionEntities = interactionEntities,
        lastImageUpdate = lastImageUpdate) {
    var status = MysteryBox.Status.CLOSED
    var lastClickInteraction: Long = 0
    var level: Level? = null
    var buyer: Entity? = null
    abstract val price: Int
}