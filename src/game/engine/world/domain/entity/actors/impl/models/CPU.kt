package game.engine.world.domain.entity.actors.impl.models

import game.engine.world.domain.entity.geo.Direction

interface CPU {
    fun chooseDirection(forceChange: Boolean): Direction?
    fun changeDirection()
}