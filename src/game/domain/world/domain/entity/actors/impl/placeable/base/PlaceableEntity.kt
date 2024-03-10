package game.domain.world.domain.entity.actors.impl.placeable.base

import game.domain.world.domain.entity.actors.abstracts.base.EntityState
import game.domain.world.domain.entity.actors.impl.blocks.movable_block.MovableBlock
import game.network.entity.EntityNetwork
import game.network.entity.PlaceableEntityNetwork
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState
import game.domain.world.domain.entity.actors.impl.placeable.base.state.PlaceableEntityState
import game.domain.world.domain.entity.geo.Coordinates

abstract class PlaceableEntity : MovableBlock {
    constructor(caller: Character) : super(Coordinates.getCenterCoordinatesOfEntity(caller)) {
        this.state.caller = caller
    }

    constructor(id: Long, caller: Character) : super(id) {
        this.state.caller = caller
    }

    constructor(coordinates: Coordinates?, caller: Character) : super(coordinates) {
        this.state.caller = caller
    }

    override val state: PlaceableEntityState = PlaceableEntityState(entity = this)

    override fun toDto(): EntityNetwork {
        return PlaceableEntityNetwork(
                info.id,
                info.position,
                info.type.ordinal,
                state.caller.info.id
        )
    }
}