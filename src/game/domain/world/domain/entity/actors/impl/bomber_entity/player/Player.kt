package game.domain.world.domain.entity.actors.impl.bomber_entity.player

import game.audio.SoundModel
import game.data.data.DataInputOutput
import game.domain.events.game.HealthUpdatedEvent
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.base.EntityInfo
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.IBomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityProperties
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityState
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.graphics.PlayerImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic.PlayerLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.state.PlayerState
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log
import game.utils.skin.SkinUtilities

class Player : BomberEntity {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: IBomberEntityLogic = PlayerLogic(entity = this)
    override val state: PlayerState = PlayerState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val image: PlayerImageModel = PlayerImageModel(entity = this)
    override val properties: BomberEntityProperties = object : BomberEntityProperties(
            types = EntityTypes.BomberEntity,
            skinId = SkinUtilities.getSkinId(DataInputOutput.getInstance().skin),
    ) {
        override var name: String? = DataInputOutput.getInstance().username
    }

    internal object DEFAULT {
        val DEATH_SOUND: SoundModel
            get() = SoundModel.PLAYER_DEATH
    }

    override fun updateInfo(info: Map<String, String>) {
        super.updateInfo(info)

        val hp = info.getOrTrim("hp")?.toInt()

        hp?.let {
            state.hp = it
            HealthUpdatedEvent().invoke(null)
        }
    }
}