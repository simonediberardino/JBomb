package game.http.dao

import game.entity.models.Coordinates

open class CharacterDao(
        entityId: Long,
        entityLocation: Coordinates?,
        entityType: Int,
        val direction: Int)
    : EntityDao(entityId, entityLocation, entityType) {
}