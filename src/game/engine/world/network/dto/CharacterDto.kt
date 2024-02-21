package game.engine.world.network.dto

import game.engine.world.domain.entity.geo.Coordinates

open class CharacterDto(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val direction: Int)
    : EntityDto(entityId, entityLocation, entityType)