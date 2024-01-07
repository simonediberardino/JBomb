package game.http.dao

import game.entity.models.Coordinates

class BomberEntityDao(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        direction: Int,
        val currExplosionLength: Int,
        val currentBombs: Int,

) :
        CharacterDao(entityId, entityLocation, entityType, direction) {
}