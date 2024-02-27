package game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.logic.IBomberEntityLogic
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityState
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.graphics.PlayerImageModel
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.graphics.RemotePlayerImageModel
import game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player.properties.RemotePlayerProperties
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Paths

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