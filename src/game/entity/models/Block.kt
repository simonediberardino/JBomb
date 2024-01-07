package game.entity.models

import game.ui.panels.game.PitchPanel

abstract class Block(coordinates: Coordinates?) : Entity(coordinates) {
    override val size: Int
        get() = SIZE
    override fun eliminated() = destroy()
    open fun destroy() = despawnAndNotify()
    override fun onAttackReceived(value: Int) = destroy()

    companion object {
        val SIZE = PitchPanel.GRID_SIZE
    }
}