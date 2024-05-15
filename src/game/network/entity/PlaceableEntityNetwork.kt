package game.network.entity

import game.domain.world.domain.entity.geo.Coordinates

class PlaceableEntityNetwork(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        isImmune: Boolean,
        val callerId: Long)
    : EntityNetwork(entityId, entityLocation, entityType, isImmune)