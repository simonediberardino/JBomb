package game.engine.world.entity.impl.bomb

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.engine.world.geo.Direction
import game.engine.world.entity.impl.models.Entity
import game.engine.world.entity.impl.models.Explosive
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

    override val type: EntityTypes = EntityTypes.PistolExplosion
}