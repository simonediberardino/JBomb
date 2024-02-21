package game.engine.world.domain.entity.actors.impl.enemies.npcs

import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.ui.panels.game.PitchPanel
import game.utils.Paths.enemiesFolder

class YellowBall : IntelligentEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val entitiesAssetsPath: String get() ="$enemiesFolder/yellow_ball/yellow_ball"

    override fun getImageDirections(): List<Direction> = listOf(Direction.RIGHT, Direction.LEFT)

    override fun getCharacterOrientedImages(): Array<String> = Array(4) { index ->
        "$enemiesFolder/yellow_ball/yellow_ball_${imageDirection.toString().lowercase()}_$index.png"
    }

    override val size: Int
        get() = PitchPanel.COMMON_DIVISOR * 2

    override val type: EntityTypes
        get() = EntityTypes.YellowBall
}