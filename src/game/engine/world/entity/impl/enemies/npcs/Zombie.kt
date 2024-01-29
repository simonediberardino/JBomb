package game.engine.world.entity.impl.enemies.npcs

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.utils.Paths.enemiesFolder

class Zombie : IntelligentEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getSpeed(): Float = 0.5f

    override fun getMaxHp(): Int = 300

    override val entitiesAssetsPath: String get() ="$enemiesFolder/zombie"

    override fun getCharacterOrientedImages(): Array<String> = Array(4) {
        "$entitiesAssetsPath/zombie_${imageDirection.toString().lowercase()}_$it.png"
    }

    override val type: EntityTypes
        get() = EntityTypes.Zombie
}