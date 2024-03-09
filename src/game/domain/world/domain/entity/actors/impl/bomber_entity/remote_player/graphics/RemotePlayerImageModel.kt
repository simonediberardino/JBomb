package game.domain.world.domain.entity.actors.impl.bomber_entity.player.graphics

import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player.RemotePlayer
import game.utils.file_system.Paths

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