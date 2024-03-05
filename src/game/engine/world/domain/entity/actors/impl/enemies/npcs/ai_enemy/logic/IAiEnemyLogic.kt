package game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic

import game.engine.world.domain.entity.geo.Direction

interface IAiEnemyLogic {
    fun chooseDirection(forceChange: Boolean): Direction?
    fun changeDirection()
    fun process()
}