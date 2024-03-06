package game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player

import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.graphics.RemotePlayerImageModel
import game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player.properties.RemotePlayerProperties
import game.engine.world.domain.entity.geo.Coordinates

class RemotePlayer : BomberEntity {
    private val skinId: Int

    constructor(coordinates: Coordinates?, skinId: Int = 1) : super(coordinates) {
        this.skinId = skinId
    }

    constructor(coordinates: Coordinates?, id: Long, skinId: Int) : this(coordinates, skinId) {
        this.info.id = id
    }

    constructor(id: Long) : this(null, id, 1)


    override val image: RemotePlayerImageModel = RemotePlayerImageModel(entity = this)
    override val properties: RemotePlayerProperties = RemotePlayerProperties()
}