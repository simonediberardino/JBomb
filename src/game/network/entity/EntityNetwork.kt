package game.network.entity

import game.engine.world.domain.entity.geo.Coordinates

open class EntityNetwork(
        val entityId: Long,
        val entityLocation: Coordinates?,
        val entityType: Int
)