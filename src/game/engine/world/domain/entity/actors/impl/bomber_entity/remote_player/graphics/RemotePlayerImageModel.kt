package game.engine.world.domain.entity.actors.impl.bomber_entity.player.graphics

import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player.RemotePlayer
import game.storage.data.DataInputOutput
import game.utils.Paths

class RemotePlayerImageModel(
        entity: RemotePlayer
) : CharacterImageModel(
        entity = entity,
        entitiesAssetsPath = "${Paths.entitiesFolder}/player/skin${entity.properties.skinId}"
) {
    override fun characterOrientedImages(): Array<String> {
        entity as RemotePlayer

        return Array(4) { index ->
            "$entitiesAssetsPath/player_${entity.state.imageDirection.toString().lowercase()}_${index}.png"
        }
    }
}