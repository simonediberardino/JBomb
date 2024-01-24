package game.entity.player

import game.Bomberman
import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.utils.Paths

class RemotePlayer : BomberEntity {
    private val skinId: Int

    constructor(coordinates: Coordinates?, skinId: Int = 1) : super(coordinates) {
        this.skinId = skinId
    }

    constructor(coordinates: Coordinates?, id: Long, skinId: Int) : this(coordinates, skinId) {
        this.entityInfo.id = id
    }

    constructor(id: Long) : this(null, id, 1)

    // TODO
    override val entitiesAssetsPath: String get() ="${Paths.entitiesFolder}/player/skin$skinId"

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) { index ->
            "$entitiesAssetsPath/player_${imageDirection.toString().lowercase()}_${index}.png"
        }
    }

    override val maxExplosionDistance: Int
        get() = Bomberman.getMatch().player?.maxBombs ?: 0 // TODO

    override val type: EntityTypes
        get() = EntityTypes.RemotePlayer
}