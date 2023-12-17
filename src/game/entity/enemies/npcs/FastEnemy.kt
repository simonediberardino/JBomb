package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.ui.panels.game.PitchPanel
import game.utils.Paths.enemiesFolder
import java.util.*

/**
 * An enemy with increased speed multiplier;
 */
class FastEnemy : IntelligentEnemy {
    constructor() : super() {
        hitboxSizeToHeightRatio = 0.527f
    }

    constructor(coordinates: Coordinates?) : super(coordinates) {
        hitboxSizeToHeightRatio = 0.527f
    }

    override fun getBasePath(): String {
        return "$enemiesFolder/fast_enemy/fast_enemy"
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) { index ->
            "${basePath}_${imageDirection.toString().lowercase()}_$index.png"
        }
    }

    override fun getSpeed(): Float {
        return 1f
    }

    override fun getImageDirections(): List<Direction> {
        return listOf(Direction.RIGHT, Direction.LEFT)
    }

    override fun getSize(): Int {
        return PitchPanel.COMMON_DIVISOR * 2
    }

    override fun getType(): EntityTypes {
        return EntityTypes.FastEnemy
    }
}