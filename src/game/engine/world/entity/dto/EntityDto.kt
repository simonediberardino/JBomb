package game.engine.world.entity.dto

import game.engine.world.geo.Coordinates

open class EntityDto(
        val entityId: Long,
        val entityLocation: Coordinates?,
        val entityType: Int
)