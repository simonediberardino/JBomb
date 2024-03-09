package game.domain.world.domain.entity.actors.impl.models

import game.domain.world.domain.entity.geo.Direction

interface CPU {
    fun chooseDirection(forceChange: Boolean): Direction?
    fun changeDirection()
}