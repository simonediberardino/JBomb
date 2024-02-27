package game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.logic

import game.engine.world.domain.entity.actors.abstracts.moving_entity.logic.IMovingEntityLogic
import game.engine.world.domain.entity.actors.abstracts.moving_entity.logic.MovingEntityLogic
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction

interface IExplosionLogic : IMovingEntityLogic {
    fun explode()
    fun canExpand() : Boolean
    fun expandBomb(d: Direction, stepSize: Int)
    fun onObstacle(coordinates: Coordinates)
}