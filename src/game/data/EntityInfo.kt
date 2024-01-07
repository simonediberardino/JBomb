package game.data

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Entity
import java.util.Properties
import kotlin.reflect.KClass

data class EntityInfo(
        var id: Long?,
        var coords: Coordinates?,
        var type: EntityTypes?
) {
    constructor() : this(null, null, null)
}