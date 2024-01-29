package game.engine.world.entity.impl.enemies.npcs

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.engine.world.geo.Direction
import game.engine.ui.panels.game.PitchPanel
import game.utils.Paths.enemiesFolder

/**
 * An enemy with increased speed multiplier;
 */
class FastEnemy : IntelligentEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    init {
        hitboxSizeToHeightRatio = 0.527f
    }

    override val entitiesAssetsPath: String get() = "$enemiesFolder/fast_enemy/fast_enemy"

    override fun getCharacterOrientedImages(): Array<String> = Array(4) { index ->
        "${entitiesAssetsPath}_${imageDirection.toString().lowercase()}_$index.png"
    }

    override fun getSpeed(): Float = 1f

    override fun getImageDirections(): List<Direction> = listOf(Direction.RIGHT, Direction.LEFT)

    override val size: Int
        get() = PitchPanel.COMMON_DIVISOR * 2

    override val type: EntityTypes
        get() = EntityTypes.FastEnemy
}