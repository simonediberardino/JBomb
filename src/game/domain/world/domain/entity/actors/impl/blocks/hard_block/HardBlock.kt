package game.domain.world.domain.entity.actors.impl.blocks.hard_block

import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block

abstract class HardBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
}
