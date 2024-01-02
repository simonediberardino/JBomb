package game.entity.bomb

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.entity.models.Entity
import game.entity.models.Explosive
import game.utils.Paths

class FireExplosion : AbstractExplosion {
    constructor(
            owner: Entity,
            coordinates: Coordinates,
            direction: Direction,
            explosive: Explosive) : super(owner, coordinates, direction, explosive)

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

    override fun getEntitiesAssetsPath(): String = "${Paths.entitiesFolder}/bomb/flame"

    override fun getType(): EntityTypes = EntityTypes.FireExplosion

    override val explosionClass: Class<out AbstractExplosion>
        get() = javaClass
}
