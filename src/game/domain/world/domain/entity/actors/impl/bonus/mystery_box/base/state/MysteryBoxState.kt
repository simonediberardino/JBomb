package game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.state

import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.domain.world.domain.entity.actors.impl.models.State

abstract class MysteryBoxState(
        entity: Entity,
        size: Int = MysteryBox.DEFAULT.SIZE,
        var level: () -> Level?,
        var buyer: () -> BomberEntity?
) : BlockEntityState(entity = entity,
        size = size
) {
    var status = MysteryBox.Status.CLOSED
    var lastClickInteraction: Long = 0
    abstract val price: Int
}