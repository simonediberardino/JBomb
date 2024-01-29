package game.engine.world.entity.dto

import game.engine.world.geo.Coordinates

open class CharacterDto(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val direction: Int)
    : EntityDto(entityId, entityLocation, entityType)