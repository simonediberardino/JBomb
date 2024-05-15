package game.network.entity

import game.domain.world.domain.entity.geo.Coordinates

open class CharacterNetwork(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        isImmune: Boolean,
        val direction: Int)
    : EntityNetwork(entityId, entityLocation, entityType, isImmune)