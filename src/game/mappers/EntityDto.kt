package game.mappers

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.abstracts.placeable.base.PlaceableEntity
import game.domain.world.types.EntityTypes
import game.network.entity.BomberEntityNetwork
import game.network.entity.CharacterNetwork
import game.network.entity.EntityNetwork
import game.network.entity.PlaceableEntityNetwork

fun EntityTypes.toEntity(id: Long, extra:  Map<String, String>? = null) : Entity? {
    return EntityFactory.instance.toEntity(this, id, extra)
}

fun Entity.dtoToEntityNetwork(): EntityNetwork {
    return EntityNetwork(
            entityId = info.id,
            entityLocation = info.position.toAbsolute(),
            entityType = info.type.ordinal,
            isImmune = state.isImmune
    )
}

fun BomberEntity.dtoToEntityNetwork(): BomberEntityNetwork {
    return BomberEntityNetwork(
            entityId = info.id,
            entityLocation = info.position.toAbsolute(),
            entityType = info.type.ordinal,
            direction = state.direction.ordinal,
            currExplosionLength = state.currExplosionLength,
            currentBombs = state.currentBombs,
            skinId = properties.skinId,
            isImmune = state.isImmune,
            name = properties.name,
            hp = state.hp
    )
}

fun Character.dtoToEntityNetwork(): CharacterNetwork {
    return CharacterNetwork(
            entityId = info.id,
            entityLocation = info.position.toAbsolute(),
            entityType = info.type.ordinal,
            isImmune = state.isImmune,
            direction = state.direction.ordinal,
            name = properties.name
    )
}

fun PlaceableEntity.dtoToEntityNetwork(): PlaceableEntityNetwork {
    return PlaceableEntityNetwork(
            entityId = info.id,
            entityLocation = info.position.toAbsolute(),
            entityType = info.type.ordinal,
            callerId = state.caller?.info?.id ?: -1,
            isImmune = state.isImmune
    )
}