package game.engine.world.domain.entity.actors.impl.blocks

import game.engine.world.domain.entity.actors.impl.models.Block
import game.engine.world.domain.entity.geo.Coordinates

abstract class MovableBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
}
