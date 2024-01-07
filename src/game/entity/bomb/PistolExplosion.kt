package game.entity.bomb

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.entity.models.Entity
import game.entity.models.Explosive
import game.utils.Paths
import game.values.DrawPriority

class PistolExplosion : AbstractExplosion {
    constructor(
            owner: Entity,
            coordinates: Coordinates,
            direction: Direction,
            explosive: Explosive) : super(owner, coordinates, direction, 0, explosive)

    constructor(
            owner: Entity,
            coordinates: Coordinates,
            direction: Direction,
            distanceFromBomb: Int,
            explosive: Explosive) : super(owner, coordinates, direction, distanceFromBomb, explosive)

    constructor(
            owner: Entity,
            coordinates: Coordinates,
            direction: Direction,
            distanceFromExplosive: Int,
            explosive: Explosive,
            canExpand: Boolean) : super(owner, coordinates, direction, distanceFromExplosive, explosive, canExpand)

    override val entitiesAssetsPath: String get() ="${Paths.entitiesFolder}/bomb/flame"

    override val drawPriority: DrawPriority
        get() = DrawPriority.DRAW_PRIORITY_1

    override val explosionClass: Class<out AbstractExplosion>
        get() = javaClass

    override fun shouldHideCenter(): Boolean = true

    override val type: EntityTypes
        get() = EntityTypes.PistolExplosion
}