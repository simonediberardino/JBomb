package game.engine.world.domain.entity.actors.impl.enemies.npcs

import game.engine.world.domain.entity.actors.impl.enemies.ai_enemy.AiEnemy
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Paths.enemiesFolder

class Zombie : AiEnemy {
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