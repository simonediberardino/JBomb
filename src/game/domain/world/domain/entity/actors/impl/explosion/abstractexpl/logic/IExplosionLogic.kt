package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic

import game.domain.world.domain.entity.actors.abstracts.moving_entity.logic.IMovingEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction

interface IExplosionLogic : IMovingEntityLogic {
    fun explode(): AbstractExplosion
    fun canExpand() : Boolean
    fun expandBomb(d: Direction, stepSize: Int)
    fun onObstacle(coordinates: Coordinates)
}