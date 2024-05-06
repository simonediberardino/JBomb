package game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic

import game.domain.world.domain.entity.geo.Direction

interface IAiLogic {
    fun chooseDirection(forceChange: Boolean): Direction?
    fun changeDirection()
    fun process()
}