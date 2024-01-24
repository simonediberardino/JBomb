package game.entity.placeable

import game.entity.blocks.MovableBlock
import game.entity.models.Character
import game.entity.models.Coordinates

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

    override val extras: Map<String, String>
        get() = hashMapOf(
                "callerId" to caller.entityInfo.id.toString()
        )
}