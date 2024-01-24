package game.data

import game.entity.EntityTypes
import game.entity.models.Coordinates

data class EntityInfo(
        var id: Long,
        var position: Coordinates,
        var type: EntityTypes
) {
    constructor() : this(-1, Coordinates(-1, -1), EntityTypes.Entity)
}