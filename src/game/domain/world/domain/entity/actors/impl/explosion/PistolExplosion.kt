package game.domain.world.domain.entity.actors.impl.explosion

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.graphics.ExplosionImageModel
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.state.ExplosionProperties
import game.domain.world.domain.entity.actors.abstracts.models.Explosive
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic.ExplosionLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic.IExplosionLogic
import game.utils.file_system.Paths
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

    override val properties: ExplosionProperties = ExplosionProperties(
            types = EntityTypes.PistolExplosion,
            explosionClass = javaClass,
            ignoreCenter = true
    )

    override val image: ExplosionImageModel = ExplosionImageModel(
            entity = this,
            entitiesAssetsPath = "${Paths.entitiesFolder}/bomb/flame"
    )

    override val logic: IExplosionLogic = object: ExplosionLogic(entity = this) {
        override fun canBeInteractedBy(e: Entity?): Boolean = e != entity.state.owner && super.canBeInteractedBy(e)
        override fun canInteractWith(e: Entity?): Boolean = e != entity.state.owner && super.canInteractWith(e)
    }
}