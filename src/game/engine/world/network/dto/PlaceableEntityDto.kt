package game.engine.world.network.dto

import game.engine.world.domain.entity.geo.Coordinates

class PlaceableEntityDto(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val callerId: Long)
    : EntityDto(entityId, entityLocation, entityType)