package game.engine.world.domain.entity.actors.impl.placeable

import game.engine.world.domain.entity.actors.impl.blocks.movable_block.MovableBlock
import game.network.entity.EntityNetwork
import game.network.entity.PlaceableEntityNetwork
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.geo.Coordinates

abstract class PlaceableEntity : MovableBlock {
    val caller: Character

    constructor(caller: Character) : super(Coordinates.getCenterCoordinatesOfEntity(caller)) {
        this.caller = caller
    }

    constructor(id: Long, caller: Character) : super(id) {
        this.caller = caller
    }

    constructor(coordinates: Coordinates?, caller: Character) : super(coordinates) {
        this.caller = caller
    }

    override fun toDto(): EntityNetwork {
        return PlaceableEntityNetwork(
                info.id,
                info.position,
                info.type.ordinal,
                caller.info.id
        )
    }
}