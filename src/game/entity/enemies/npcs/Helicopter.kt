package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.utils.Paths.enemiesFolder
import java.util.*

class Helicopter : FlyingEnemy() {
    override fun getBasePath(): String {
        return "$enemiesFolder/heli"
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(3) { index ->
            "$basePath/heli_${imageDirection.toString().lowercase()}_$index.gif"
        }
    }

    override fun getType(): EntityTypes {
        return EntityTypes.Helicopter
    }
}