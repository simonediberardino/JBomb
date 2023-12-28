package game.entity.player

import game.data.DataInputOutput
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.sound.SoundModel
import game.utils.Paths

class RemotePlayer(coordinates: Coordinates?, private val skinId: Int) : BomberEntity(coordinates) {
    // TODO
    override fun getEntitiesAssetsPath(): String = "${Paths.entitiesFolder}/player/$skinId"

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) { index ->
            "$entitiesAssetsPath/player_${imageDirection.toString().lowercase()}_${index}.png"
        }
    }

    override val maxExplosionDistance: Int
        get() = TODO("Not yet implemented")

}