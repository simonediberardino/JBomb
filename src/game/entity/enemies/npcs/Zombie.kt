package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.utils.Paths.enemiesFolder

class Zombie : IntelligentEnemy {

    constructor() : super() {}
    constructor(coordinates: Coordinates?) : super(coordinates) {}

    override fun getSpeed(): Float = 0.5f

    override fun getMaxHp(): Int = 300

    override fun getEntitiesAssetsPath(): String = "$enemiesFolder/zombie"

    override fun getCharacterOrientedImages(): Array<String> = Array(4) {
        "$entitiesAssetsPath/zombie_${imageDirection.toString().lowercase()}_$it.png"
    }

    override fun getType(): EntityTypes = EntityTypes.Zombie
}