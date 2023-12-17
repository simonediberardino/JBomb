package game.entity.models

interface ICPU {
    fun chooseDirection(forceChange: Boolean): Direction?
    fun changeDirection()
}