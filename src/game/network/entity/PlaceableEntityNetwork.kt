package game.network.entity

import game.engine.world.domain.entity.geo.Coordinates

class PlaceableEntityNetwork(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val callerId: Long)
    : EntityNetwork(entityId, entityLocation, entityType)