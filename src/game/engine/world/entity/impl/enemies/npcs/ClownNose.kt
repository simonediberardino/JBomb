package game.engine.world.entity.impl.enemies.npcs

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.engine.world.geo.Direction
import game.engine.world.geo.EnhancedDirection
import game.engine.sound.SoundModel
import game.utils.Paths.enemiesFolder

class ClownNose : Orb {
    constructor(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : super(coordinates, enhancedDirection)
    constructor(coordinates: Coordinates?, direction: Direction?) : super(coordinates, direction)
    constructor() : this(null, null as Direction?)
    constructor(id: Long) : super(id)

    override val entitiesAssetsPath: String get() ="$enemiesFolder/clown/clown_orb.png"

    /**
     * Returns an array of the skin of the Orb.
     *
     * @return an array of the skin of the Orb
     */
    override fun getCharacterOrientedImages(): Array<String> = arrayOf(entitiesAssetsPath)

    override fun getDeathSound(): SoundModel = SoundModel.CLOWN_NOSE_DEATH

    override val type: EntityTypes = EntityTypes.ClownNose
}