package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.utils.Paths.enemiesFolder

class Helicopter : FlyingEnemy() {
    override fun getEntitiesAssetsPath(): String = "$enemiesFolder/heli"

    override fun getCharacterOrientedImages(): Array<String> = Array(3) { index ->
        "$entitiesAssetsPath/heli_${imageDirection.toString().lowercase()}_$index.gif"
    }

    override fun getType(): EntityTypes = EntityTypes.Helicopter
}