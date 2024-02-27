package game.engine.world.domain.entity.actors.impl.bomber_entity.player.graphics

import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.storage.data.DataInputOutput
import game.utils.Paths

class PlayerImageModel(
        entity: Player
) : CharacterImageModel(
        entity = entity,
        entitiesAssetsPath = "${Paths.entitiesFolder}/player/${DataInputOutput.getInstance().skin}"
) {
    override fun characterOrientedImages(): Array<String> {
        entity as Player

        return Array(4) { index ->
            "$entitiesAssetsPath/player_${entity.state.imageDirection.toString().lowercase()}_${index}.png"
        }
    }
}