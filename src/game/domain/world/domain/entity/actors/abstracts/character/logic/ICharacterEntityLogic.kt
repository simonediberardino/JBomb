package game.domain.world.domain.entity.actors.abstracts.character.logic

import game.input.Command
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.abstracts.moving_entity.logic.IMovingEntityLogic
import game.domain.world.domain.entity.geo.Direction

interface ICharacterEntityLogic : IMovingEntityLogic {
    fun isAlive() : Boolean
    fun setAliveState(alive: Boolean)
    fun updateMovementDirection(direction: Direction)
    fun onStep()
    fun move(direction: Direction) : Boolean
    fun damageAnimation()
    fun onEliminated()
    fun onHit(damage: Int)
    fun onExplosion(explosion: AbstractExplosion)
    fun handleCommand(command: Command)
    fun doAttack()
    fun handleMoveCommand(command: Command, oppositeDirection1: Direction, oppositeDirection2: Direction)
    fun overpassBlock(entitiesOpposite1: List<Entity>, entitiesOpposite2: List<Entity>, direction1: Direction, direction2: Direction)
    fun executeCommandQueue()
    fun onEndedDeathAnimation()
}