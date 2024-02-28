package game.engine.world.domain.entity.actors.impl.blocks.hard_block

import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.EntityState
import game.engine.world.domain.entity.actors.impl.blocks.base_block.Block
import game.engine.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.engine.world.types.EntityTypes
import game.values.DrawPriority

 abstract class HardBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
}
