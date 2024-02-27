package game.engine.world.domain.entity.actors.impl.bomber_entity.player

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
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

class Player : BomberEntity {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: IBomberEntityLogic = BomberEntityLogic(entity = this)
    override val state: BomberEntityState = BomberEntityState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(character = this)
    override val image: PlayerImageModel = PlayerImageModel(entity = this)
    override val info: EntityInfo = EntityInfo()
    override val properties: CharacterEntityProperties = CharacterEntityProperties(types = EntityTypes.BomberEntity)
}