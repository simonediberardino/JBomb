package game.engine.world.domain.entity.actors.impl.bomb

import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.graphics.ExplosionImageModel
import game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.state.ExplosionProperties
import game.engine.world.domain.entity.actors.impl.models.Explosive
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

    override val properties: ExplosionProperties = ExplosionProperties(
            types = EntityTypes.PistolExplosion,
            explosionClass = javaClass,
            drawPriority = DrawPriority.DRAW_PRIORITY_1,
            ignoreCenter = true
    )

    override val image: ExplosionImageModel = ExplosionImageModel(
            entity = this,
            entitiesAssetsPath = "${Paths.entitiesFolder}/bomb/flame"
    )
}