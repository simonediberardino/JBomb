package game.engine.world.domain.entity.actors.impl.blocks

import game.engine.world.domain.entity.actors.impl.models.Block
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity

abstract class HardBlock : Block {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf()
}
