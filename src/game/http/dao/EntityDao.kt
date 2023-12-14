package game.http.dao

import game.entity.models.Coordinates

class EntityDao(
        val entityId: Long,
        val entityLocation: Coordinates,
        val entityType: Int
)