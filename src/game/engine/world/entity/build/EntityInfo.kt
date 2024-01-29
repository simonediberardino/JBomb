package game.engine.world.entity.build

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates

data class EntityInfo(
        var id: Long,
        var position: Coordinates,
        var type: EntityTypes
) {
    constructor() : this(-1, Coordinates(-1, -1), EntityTypes.Entity)
}