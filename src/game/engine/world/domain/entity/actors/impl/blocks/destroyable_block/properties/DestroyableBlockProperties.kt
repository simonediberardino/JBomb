package game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.properties

import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.pickups.powerups.PowerUp
import game.engine.world.dto.EntityTypes
import game.values.DrawPriority

class DestroyableBlockProperties : EntityProperties(
        priority = DrawPriority.DRAW_PRIORITY_1,
        type = EntityTypes.DestroyableBlock
)