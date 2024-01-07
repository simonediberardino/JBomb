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
    constructor(id: Long) : this() {
        this.id = id
    }

    override val entitiesAssetsPath: String get() ="$enemiesFolder/clown/clown_orb.png"

    /**
     * Returns an array of the skin of the Orb.
     *
     * @return an array of the skin of the Orb
     */
    override fun getCharacterOrientedImages(): Array<String> = arrayOf(
            entitiesAssetsPath
    )

    override fun getDeathSound(): SoundModel = SoundModel.CLOWN_NOSE_DEATH

    override val type: EntityTypes
        get() = EntityTypes.ClownNose
}