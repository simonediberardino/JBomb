package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.utils.Paths.enemiesFolder

class Eagle() : FlyingEnemy() {
    constructor(id: Long) : this() {
        this.id = id
    }

    override val entitiesAssetsPath: String get() ="$enemiesFolder/eagle"

    override fun getCharacterOrientedImages(): Array<String> = Array(3) { index ->
        "$entitiesAssetsPath/eagle_${imageDirection.toString().lowercase()}_$index.png"
    }

    override val type: EntityTypes
        get() = EntityTypes.Eagle
}