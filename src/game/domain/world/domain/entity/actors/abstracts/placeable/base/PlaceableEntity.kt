package game.domain.world.domain.entity.actors.abstracts.placeable.base

import game.domain.world.domain.entity.actors.impl.blocks.movable_block.MovableBlock
import game.domain.world.domain.entity.actors.abstracts.placeable.base.state.PlaceableEntityState
import game.domain.world.domain.entity.geo.Coordinates
import game.mappers.dtoToEntityNetwork
import game.network.entity.PlaceableEntityNetwork

abstract class PlaceableEntity(
        coordinates: Coordinates? = null
) : MovableBlock(coordinates) {

    constructor() : this(null)

    constructor(id: Long) : this(null) {
        this.info.id = id
    }

    abstract override val state: PlaceableEntityState

    override fun toEntityNetwork(): PlaceableEntityNetwork {
        return this.dtoToEntityNetwork()
    }
}