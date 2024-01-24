package game.entity.blocks

import game.entity.models.Block
import game.entity.models.Coordinates

abstract class MovableBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
}
