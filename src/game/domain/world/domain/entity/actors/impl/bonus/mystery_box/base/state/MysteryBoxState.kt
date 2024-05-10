package game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.state

import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.domain.world.domain.entity.actors.impl.models.State

abstract class MysteryBoxState(
        entity: Entity,
        isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        state: State? = Entity.DEFAULT.STATE,
        isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        size: Int = MysteryBox.DEFAULT.SIZE,
        alpha: Float = Entity.DEFAULT.ALPHA,
        interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE,
        var level: () -> Level?,
        var buyer: () -> Entity?
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
    abstract val price: Int
}