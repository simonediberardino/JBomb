package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.logic.IMovingEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction

interface IExplosionLogic : IMovingEntityLogic {
    fun explode(lastExplosion : AbstractExplosion? ): AbstractExplosion
    fun explode(): AbstractExplosion
    fun canExpand() : Boolean
    fun isBlockedOnRight(): Boolean
    fun isBlockedOnLeft(): Boolean
    fun isBlockedOnDown(): Boolean
    fun isBlockedOnUp(): Boolean
    fun expandBomb(d: Direction, stepSize: Int)
    fun onObstacle(coordinates: Coordinates)
}