package game.engine.world.entity.impl.bomb

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.engine.world.geo.Direction
import game.engine.world.entity.impl.models.Entity
import game.engine.world.entity.impl.models.Explosive
import game.utils.Paths

class ConfettiExplosion : AbstractExplosion {
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

    override val explosionClass: Class<out AbstractExplosion>
        get() = javaClass

    override val type: EntityTypes
        get() = EntityTypes.ConfettiExplosion

    override val entitiesAssetsPath: String get() ="${Paths.enemiesFolder}/clown/clown_explosion/clown_explosion"
}
