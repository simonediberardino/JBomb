package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.ui.panels.game.PitchPanel
import game.utils.Paths.enemiesFolder
import java.util.*

class YellowBall : IntelligentEnemy {
    constructor() : super()
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getBasePath(): String {
        return "$enemiesFolder/yellow_ball/yellow_ball"
    }

    override fun getImageDirections(): List<Direction> {
        return listOf(Direction.RIGHT, Direction.LEFT)
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) { index ->
            "$enemiesFolder/yellow_ball/yellow_ball_${imageDirection.toString().lowercase()}_$index.png"
        }
    }

    override fun getSize(): Int {
        return PitchPanel.COMMON_DIVISOR * 2
    }

    override fun getType(): EntityTypes {
        return EntityTypes.YellowBall
    }
}