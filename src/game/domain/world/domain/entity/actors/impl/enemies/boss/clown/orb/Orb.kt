package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb

import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.logic.OrbEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityProperties
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityState
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.geo.EnhancedDirection
import game.values.DrawPriority

/**
 * The Orb class represents a little enemy entity that moves in a specific direction.
 * It can be instantiated with either an EnhancedDirection or a Direction, but not both.
 * The Orb class implements the Transparent and Particle interfaces.
 */
abstract class Orb(
        coordinates: Coordinates?,
        enhancedDirection: EnhancedDirection? = null,
        direction: Direction? = null
) : Enemy(coordinates) {
    constructor(coordinates: Coordinates?, direction: Direction?) : this(
            coordinates = coordinates,
            enhancedDirection = null as EnhancedDirection?,
            direction = direction)

    constructor(id: Long) : this(null, null, null) {
        info.id = id
    }

    constructor(coordinates: Coordinates?) : this(coordinates, null, null)


    override val state: OrbEntityState = object : OrbEntityState(entity = this) {
        init {
            this.enhancedDirection = enhancedDirection
            if (direction != null) {
                this.direction = direction
            }
        }
    }

    override val logic: OrbEntityLogic = OrbEntityLogic(entity = this)
    abstract override val properties: OrbEntityProperties

    internal object DEFAULT {
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        const val SPEED = 1.5f
        val DRAW_PRIORITY = DrawPriority.DRAW_PRIORITY_3
        val OBSTACLES: MutableSet<Class<out Entity>>
            get() = mutableSetOf()

        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>>
            get() = mutableSetOf(BomberEntity::class.java, Bomb::class.java)
    }
}