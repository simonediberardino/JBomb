package game.network.entity

import game.engine.world.domain.entity.geo.Coordinates

open class CharacterNetwork(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val direction: Int)
    : EntityNetwork(entityId, entityLocation, entityType)