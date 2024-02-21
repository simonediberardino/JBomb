package game.engine.world.domain.entity.actors.impl.placeable

import game.engine.world.domain.entity.actors.impl.blocks.MovableBlock
import game.engine.world.network.dto.EntityDto
import game.engine.world.network.dto.PlaceableEntityDto
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.geo.Coordinates

abstract class PlaceableEntity : MovableBlock {
    protected val caller: Character

    constructor(caller: Character) : super(Coordinates.getCenterCoordinatesOfEntity(caller)) {
        this.caller = caller
    }

    constructor(id: Long, caller: Character) : super(id) {
        this.caller = caller
    }

    constructor(coordinates: Coordinates?, caller: Character) : super(coordinates) {
        this.caller = caller
    }

    override fun toDto(): EntityDto {
        return PlaceableEntityDto(
                info.id,
                info.position,
                info.type.ordinal,
                caller.info.id
        )
    }
}