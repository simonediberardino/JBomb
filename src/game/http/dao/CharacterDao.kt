package game.http.dao

import game.entity.models.Coordinates

class CharacterDao(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        val direction: Int,
        val skinStatus: Int)
    : EntityDao(entityId, entityLocation, entityType) {
}