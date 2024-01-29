package game.engine.world.entity.dto

import game.engine.world.geo.Coordinates

class BomberEntityDto(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        direction: Int,
        val currExplosionLength: Int,
        val currentBombs: Int,

        ) :
        CharacterDto(entityId, entityLocation, entityType, direction)