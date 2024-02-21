package game.engine.world.domain.entity.actors.impl.enemies.npcs

import game.engine.world.dto.EntityTypes
import game.utils.Paths.enemiesFolder

class Helicopter : FlyingEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)

    override val entitiesAssetsPath: String get() ="$enemiesFolder/heli"

    override fun getCharacterOrientedImages(): Array<String> = Array(3) { index ->
        "$entitiesAssetsPath/heli_${imageDirection.toString().lowercase()}_$index.gif"
    }

    override val type: EntityTypes
        get() = EntityTypes.Helicopter
}