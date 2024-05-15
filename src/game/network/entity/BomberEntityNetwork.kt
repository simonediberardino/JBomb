package game.network.entity

import game.domain.world.domain.entity.geo.Coordinates

class BomberEntityNetwork(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        direction: Int,
        isImmune: Boolean,
        val currExplosionLength: Int,
        val currentBombs: Int,
        val skinId: Int
) : CharacterNetwork(entityId, entityLocation, entityType, isImmune, direction)