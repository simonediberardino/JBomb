package game.network.entity

import game.engine.world.domain.entity.geo.Coordinates

class BomberEntityNetwork(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        direction: Int,
        val currExplosionLength: Int,
        val currentBombs: Int
) : CharacterNetwork(entityId, entityLocation, entityType, direction)