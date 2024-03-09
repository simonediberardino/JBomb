package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl

import game.domain.world.domain.entity.actors.impl.models.*
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityInfo
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.graphics.ExplosionGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.graphics.ExplosionImageModel
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic.ExplosionLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic.IExplosionLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.state.ExplosionProperties
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.state.ExplosionState
import game.values.DrawPriority

/**
 * An abstract class for in-game explosions;
 */
abstract class AbstractExplosion(owner: Entity,
                                 coordinates: Coordinates?,
                                 direction: Direction,
                                 distanceFromExplosive: Int,
                                 explosive: Explosive,
                                 canExpand: Boolean
) : MovingEntity(coordinates) {
    constructor(owner: Entity,
                coordinates: Coordinates?,
                direction: Direction,
                explosive: Explosive) : this(owner, coordinates, direction, 0, explosive)

    constructor(owner: Entity,
                coordinates: Coordinates?,
                direction: Direction,
                distanceFromBomb: Int,
                explosive: Explosive) : this(owner, coordinates, direction, distanceFromBomb, explosive, true)

    abstract override val properties: ExplosionProperties
    abstract override val image: ExplosionImageModel
    override val logic: IExplosionLogic = ExplosionLogic(this)
    override val info: EntityInfo = EntityInfo()
    override val graphicsBehavior: IEntityGraphicsBehavior = ExplosionGraphicsBehavior()

    override val state: ExplosionState = ExplosionState(
            entity = this,
            owner = owner,
            explosive = explosive
    )

    init {
        state.distanceFromExplosive = distanceFromExplosive
        state.explosive = explosive
        state.canExpand = canExpand
        state.owner = owner
        state.direction = direction
    }

    companion object {
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        val SPAWN_OFFSET = (PitchPanel.GRID_SIZE - SIZE) / 2
        var MAX_EXPLOSION_LENGTH = 5
        const val BOMB_STATES = 3
    }

    internal object DEFAULT {
        val IGNORE_CENTER = true
        val DRAW_PRIORITY = DrawPriority.DRAW_PRIORITY_4
        val DISTANCE_FROM_EXPLOSIVE = -1
        val CAN_EXPAND = false
        val IMAGE_REFRESH_RATE = 100
    }
}