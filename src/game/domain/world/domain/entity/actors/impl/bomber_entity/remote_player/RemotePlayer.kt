package game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player

import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityProperties
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.graphics.RemotePlayerImageModel
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes

class RemotePlayer(coordinates: Coordinates?, skinId: Int) : BomberEntity(coordinates = coordinates) {
    constructor(coordinates: Coordinates?, id: Long, skinId: Int = 0) : this(coordinates = coordinates, skinId = skinId) {
        this.info.id = id
    }

    constructor(id: Long) : this(coordinates = null, id = id)

    override val image: RemotePlayerImageModel = RemotePlayerImageModel(entity = this)

    override val properties: BomberEntityProperties = BomberEntityProperties(
            types = EntityTypes.BomberEntity,
            skinId = skinId
    )
}