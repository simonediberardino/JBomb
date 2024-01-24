package game.entity.models

import game.ui.panels.game.PitchPanel

abstract class Block : Entity {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val size: Int = SIZE

    override fun eliminated() = destroy()

    open fun destroy() = despawnAndNotify()

    override fun onAttackReceived(value: Int) = destroy()

    companion object {
        val SIZE = PitchPanel.GRID_SIZE
    }
}