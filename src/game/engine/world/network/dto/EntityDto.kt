package game.engine.world.network.dto

import game.engine.world.domain.entity.geo.Coordinates

open class EntityDto(
        val entityId: Long,
        val entityLocation: Coordinates?,
        val entityType: Int
)