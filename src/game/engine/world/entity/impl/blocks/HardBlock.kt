package game.engine.world.entity.impl.blocks

import game.engine.world.entity.impl.models.Block
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity

abstract class HardBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf()
}
