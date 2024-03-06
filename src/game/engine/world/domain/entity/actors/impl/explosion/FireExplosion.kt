package game.engine.world.domain.entity.actors.impl.explosion

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.graphics.ExplosionImageModel
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.state.ExplosionProperties
import game.engine.world.domain.entity.actors.impl.models.Explosive
import game.utils.file_system.Paths

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

    override val properties: ExplosionProperties = ExplosionProperties(
            types = EntityTypes.FireExplosion,
            explosionClass = javaClass
    )

    override val image: ExplosionImageModel = ExplosionImageModel(
            entity = this,
            entitiesAssetsPath = "${Paths.entitiesFolder}/bomb/flame"
    )
}
