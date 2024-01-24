package game.entity.blocks

import game.entity.models.Block
import game.entity.models.Coordinates
import game.entity.models.Entity

abstract class HardBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val basePassiveInteractionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf()
}
