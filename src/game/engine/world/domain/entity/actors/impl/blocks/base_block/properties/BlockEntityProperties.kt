package game.engine.world.domain.entity.actors.impl.blocks.base_block.properties

import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.types.EntityTypes
import game.values.DrawPriority

class BlockEntityProperties(type: EntityTypes) : EntityProperties(
        type = type
)