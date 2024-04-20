package game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player

import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.graphics.RemotePlayerImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player.properties.RemotePlayerProperties
import game.domain.world.domain.entity.geo.Coordinates

class RemotePlayer : BomberEntity {

    constructor(coordinates: Coordinates?, skinId: Int = 1) : super(coordinates = coordinates) {
        this.properties.skinId = skinId
    }

    constructor(coordinates: Coordinates?, id: Long, skinId: Int = 1) : this(coordinates = coordinates, skinId = skinId) {
        this.info.id = id
    }

    constructor(id: Long) : this(coordinates = null, id = id)

    override val image: RemotePlayerImageModel = RemotePlayerImageModel(entity = this)
    override val properties: RemotePlayerProperties = RemotePlayerProperties()
}