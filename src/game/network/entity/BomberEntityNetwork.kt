package game.network.entity

import game.domain.world.domain.entity.geo.Coordinates

class BomberEntityNetwork(
        entityId: Long,
        entityLocation: Coordinates,
        entityType: Int,
        direction: Int,
        isImmune: Boolean,
        name: String? = null,
        val currExplosionLength: Int,
        val currentBombs: Int,
        val skinId: Int,
        val hp: Int,
        val score: Int
) : CharacterNetwork(
        entityId = entityId,
        entityLocation = entityLocation,
        entityType = entityType,
        isImmune = isImmune,
        direction = direction,
        name = name
)