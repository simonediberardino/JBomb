package game.engine.world.network.dto

import game.engine.world.domain.entity.geo.Coordinates

class BomberEntityDto(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        direction: Int,
        val currExplosionLength: Int,
        val currentBombs: Int,

        ) :
        CharacterDto(entityId, entityLocation, entityType, direction)