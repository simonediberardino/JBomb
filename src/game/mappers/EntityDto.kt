package game.mappers

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.impl.placeable.PlaceableEntity
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.types.EntityTypes
import game.network.entity.BomberEntityNetwork
import game.network.entity.CharacterNetwork
import game.network.entity.EntityNetwork
import game.network.entity.PlaceableEntityNetwork

fun EntityTypes.toEntity(id: Long) : Entity? {
    return EntityFactory.instance.toEntity(this, id)
}

fun Entity.toEntityNetwork(): EntityNetwork {
    return EntityNetwork(
            info.id,
            info.position,
            info.type.ordinal
    )
}

fun BomberEntity.toBomberEntityNetwork(): BomberEntityNetwork {
    return BomberEntityNetwork(
            entityId = info.id,
            entityLocation = info.position,
            entityType = info.type.ordinal,
            direction = direction.ordinal,
            currExplosionLength = currExplosionLength,
            currentBombs = currentBombs
    )
}

fun Character.toCharacterNetwork(): CharacterNetwork {
    return CharacterNetwork(
            info.id,
            info.position,
            info.type.ordinal,
            state.direction?.ordinal ?: 0
    )
}

fun PlaceableEntity.toPlaceableEntityNetwork(): PlaceableEntityNetwork {
    return PlaceableEntityNetwork(
            info.id,
            info.position,
            info.type.ordinal,
            caller.info.id
    )
}