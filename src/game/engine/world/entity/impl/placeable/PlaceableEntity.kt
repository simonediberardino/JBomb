package game.engine.world.entity.impl.placeable

import game.engine.world.entity.impl.blocks.MovableBlock
import game.engine.world.entity.dto.EntityDto
import game.engine.world.entity.dto.PlaceableEntityDto
import game.engine.world.entity.impl.models.Character
import game.engine.world.geo.Coordinates

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