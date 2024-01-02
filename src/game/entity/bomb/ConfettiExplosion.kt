package game.entity.bomb

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.entity.models.Entity
import game.entity.models.Explosive
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

    override fun getType(): EntityTypes = EntityTypes.ConfettiExplosion

    override fun getEntitiesAssetsPath(): String = "${Paths.enemiesFolder}/clown/clown_explosion/clown_explosion"
}
