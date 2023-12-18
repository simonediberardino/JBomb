package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.entity.models.EnhancedDirection
import game.sound.SoundModel
import game.utils.Paths.enemiesFolder

class ClownNose : Orb {
    constructor(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : super(coordinates, enhancedDirection)
    constructor(coordinates: Coordinates?, direction: Direction?) : super(coordinates, direction)
    constructor() : this(null, null as Direction?)

    override fun getBasePath(): String {
        return "$enemiesFolder/clown/clown_orb.png"
    }

    /**
     * Returns an array of the skin of the Orb.
     *
     * @return an array of the skin of the Orb
     */
    //
    override fun getCharacterOrientedImages(): Array<String> {
        return arrayOf(
                basePath
        )
    }

    override fun getDeathSound(): SoundModel {
        return SoundModel.CLOWN_NOSE_DEATH
    }

    override fun getType(): EntityTypes {
        return EntityTypes.ClownNose
    }
}