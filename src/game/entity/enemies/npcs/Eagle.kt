package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.utils.Paths.enemiesFolder
import java.util.*

class Eagle : FlyingEnemy() {
    override fun getBasePath(): String {
        return "$enemiesFolder/eagle"
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(3) { index ->
            "$basePath/eagle_${imageDirection.toString().lowercase()}_$index.png"
        }
    }

    override fun getType(): EntityTypes {
        return EntityTypes.Eagle
    }
}