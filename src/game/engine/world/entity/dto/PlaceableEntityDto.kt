package game.engine.world.entity.dto

import game.engine.world.geo.Coordinates

class PlaceableEntityDto(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val callerId: Long)
    : EntityDto(entityId, entityLocation, entityType)