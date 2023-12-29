package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.utils.Paths.enemiesFolder

class Eagle() : FlyingEnemy() {
    constructor(id: Long) : this() {
        this.id = id
    }

    override fun getEntitiesAssetsPath(): String = "$enemiesFolder/eagle"

    override fun getCharacterOrientedImages(): Array<String> = Array(3) { index ->
        "$entitiesAssetsPath/eagle_${imageDirection.toString().lowercase()}_$index.png"
    }

    override fun getType(): EntityTypes = EntityTypes.Eagle
}