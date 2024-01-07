package game.http.dao

import game.entity.models.Coordinates

open class EntityDao(
        val entityId: Long,
        val entityLocation: Coordinates?,
        val entityType: Int
) {
    override fun toString(): String {
        return "EntityDao(entityId=$entityId, entityLocation=$entityLocation, entityType=$entityType)"
    }
}