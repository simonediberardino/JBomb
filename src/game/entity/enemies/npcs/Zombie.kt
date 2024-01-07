package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.utils.Paths.enemiesFolder

class Zombie : IntelligentEnemy {
    constructor(id: Long) : this() {
        this.id = id
    }
    constructor() : super()
    constructor(coordinates: Coordinates?) : super(coordinates) {}

    override fun getSpeed(): Float = 0.5f

    override fun getMaxHp(): Int = 300

    override val entitiesAssetsPath: String get() ="$enemiesFolder/zombie"

    override fun getCharacterOrientedImages(): Array<String> = Array(4) {
        "$entitiesAssetsPath/zombie_${imageDirection.toString().lowercase()}_$it.png"
    }

    override val type: EntityTypes
        get() = EntityTypes.Zombie
}