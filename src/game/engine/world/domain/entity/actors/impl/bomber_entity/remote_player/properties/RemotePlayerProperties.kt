package game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player.properties

import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.types.EntityTypes

class RemotePlayerProperties : CharacterEntityProperties(
        types = EntityTypes.BomberEntity
) {
    val skinId: Int = 0
}