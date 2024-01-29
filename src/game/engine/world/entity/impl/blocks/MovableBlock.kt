package game.engine.world.entity.impl.blocks

import game.engine.world.entity.impl.models.Block
import game.engine.world.geo.Coordinates

abstract class MovableBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
}
