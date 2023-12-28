package game.entity.player

import game.Bomberman
import game.entity.models.Coordinates
import game.match.BomberManMatch
import game.sound.SoundModel
import game.utils.Paths

class RemotePlayer(coordinates: Coordinates?, private val skinId: Int) : BomberEntity(coordinates) {
    constructor(coordinates: Coordinates?, id: Long, skinId: Int) : this(coordinates, skinId) {
        this.id = id
    }

    // TODO
    override fun getEntitiesAssetsPath(): String = "${Paths.entitiesFolder}/player/skin$skinId"

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) { index ->
            "$entitiesAssetsPath/player_${imageDirection.toString().lowercase()}_${index}.png"
        }
    }

    override val maxExplosionDistance: Int
        get() = Bomberman.getMatch().player?.maxBombs ?: 0 // TODO

}