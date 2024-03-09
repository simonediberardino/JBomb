package game.domain.world.domain.entity.actors.impl.blocks.movable_block

import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.geo.Coordinates

abstract class MovableBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
}
