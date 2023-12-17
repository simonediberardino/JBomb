package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.utils.Paths.enemiesFolder
import game.utils.Paths.entitiesFolder
import java.util.*

class Zombie : IntelligentEnemy {
    constructor() : super() {
        maxHp = 300
    }

    constructor(coordinates: Coordinates?) : super(coordinates) {
        maxHp = 300
    }

    override fun getSpeed(): Float {
        return 0.5f
    }

    override fun getBasePath(): String {
        return "$enemiesFolder/zombie"
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) {
            "$basePath/zombie_${imageDirection.toString().lowercase()}_$it.png"
        }
    }

    override fun getType(): EntityTypes {
        return EntityTypes.Zombie
    }
}