package game.engine.world.entity.impl.models

import game.engine.world.geo.Direction

interface CPU {
    fun chooseDirection(forceChange: Boolean): Direction?
    fun changeDirection()
}