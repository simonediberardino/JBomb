package game.engine.world.domain.entity.actors.impl.enemies.npcs

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.ui.panels.game.PitchPanel
import game.utils.Paths.enemiesFolder

class GhostEnemy : IntelligentEnemy {
    init {
        hitboxSizeToWidthRatio = 0.837f
        hitboxSizeToHeightRatio = 1f
    }

    constructor() : super()
    constructor(id: Long) : super(id)

    override fun getCharacterOrientedImages(): Array<String> =
            arrayOf("$enemiesFolder/mini_ghost/ghost_${imageDirection.toString().lowercase()}.png")

    override fun getImageDirections(): List<Direction> = listOf(Direction.RIGHT, Direction.LEFT)

    override fun getObstacles(): Set<Class<out Entity?>> = interactionsEntities

    override val size: Int
        get() = PitchPanel.COMMON_DIVISOR * 2

    override val type: EntityTypes
        get() = EntityTypes.GhostEnemy
}