package game.domain.world.domain.entity.actors.impl.bomber_entity.player

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.base.EntityInfo
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.IBomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityState
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.graphics.PlayerImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic.PlayerLogic

class Player : BomberEntity {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: IBomberEntityLogic = PlayerLogic(entity = this)
    override val state: BomberEntityState = BomberEntityState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val image: PlayerImageModel = PlayerImageModel(entity = this)
    
    override val properties: CharacterEntityProperties = CharacterEntityProperties(types = EntityTypes.BomberEntity)
}