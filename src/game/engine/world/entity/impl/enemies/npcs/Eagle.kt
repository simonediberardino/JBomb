package game.engine.world.entity.impl.enemies.npcs

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.utils.Paths.enemiesFolder

class Eagle : FlyingEnemy {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor() : super()

    override val entitiesAssetsPath: String get() ="$enemiesFolder/eagle"

    override fun getCharacterOrientedImages(): Array<String> = Array(3) { index ->
        "$entitiesAssetsPath/eagle_${imageDirection.toString().lowercase()}_$index.png"
    }

    override val type: EntityTypes
        get() = EntityTypes.Eagle
}