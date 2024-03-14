package game.domain.world.domain.entity.actors.impl.placeable.base

import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.blocks.movable_block.MovableBlock
import game.domain.world.domain.entity.actors.impl.placeable.base.state.PlaceableEntityState
import game.domain.world.domain.entity.geo.Coordinates
import game.mappers.dtoToEntityNetwork
import game.network.entity.PlaceableEntityNetwork

abstract class PlaceableEntity : MovableBlock {
    abstract override var state: PlaceableEntityState

    constructor(caller: Character) : super(Coordinates.getCenterCoordinatesOfEntity(caller)) {
        this.state = PlaceableEntityState(entity = this)
        this.state.caller = caller
    }

    constructor(id: Long, caller: Character) : super(id) {
        this.state = PlaceableEntityState(entity = this)
        this.state.caller = caller
    }

    constructor(coordinates: Coordinates?, caller: Character) : super(coordinates) {
        this.state = PlaceableEntityState(entity = this)
        this.state.caller = caller
    }

    override fun toEntityNetwork(): PlaceableEntityNetwork {
        return this.dtoToEntityNetwork()
    }
}