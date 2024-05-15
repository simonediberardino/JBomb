package game.network.entity

import game.domain.world.domain.entity.geo.Coordinates

open class EntityNetwork(
        val entityId: Long,
        val entityLocation: Coordinates?,
        val entityType: Int,
        val isImmune: Boolean
)