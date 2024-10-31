package game.domain.world.domain.entity.actors.impl.bomber_entity.base.graphics

import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player.RemotePlayer
import game.utils.file_system.Paths

class BomberEntityImageModel(entity: BomberEntity): CharacterImageModel(
    entity = entity
) {
    override fun characterOrientedImages(): Array<String> {
        entity as BomberEntity

        val entitiesAssetsPath = "${Paths.entitiesFolder}/player/skin${entity.properties.skinId}"

        return Array(4) { index ->
            "$entitiesAssetsPath/player_${entity.state.imageDirection.toString().toLowerCase()}_${index}.png"
        }
    }
}