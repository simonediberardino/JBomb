package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.models.Direction
import game.entity.models.Entity
import game.ui.panels.game.PitchPanel
import game.utils.Paths.enemiesFolder

class GhostEnemy() : IntelligentEnemy() {
    init {
        hitboxSizeToWidthRatio = 0.837f
        hitboxSizeToHeightRatio = 1f
    }

    constructor(id: Long) : this() {
        this.id = id
    }

    override fun getCharacterOrientedImages(): Array<String> =
            arrayOf("$enemiesFolder/mini_ghost/ghost_${imageDirection.toString().lowercase()}.png")

    override fun getImageDirections(): List<Direction> = listOf(Direction.RIGHT, Direction.LEFT)

    override fun getObstacles(): Set<Class<out Entity?>> = interactionsEntities

    override val size: Int
        get() = PitchPanel.COMMON_DIVISOR * 2

    override val type: EntityTypes
        get() = EntityTypes.GhostEnemy
}