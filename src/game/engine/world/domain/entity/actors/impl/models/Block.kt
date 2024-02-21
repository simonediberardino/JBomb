package game.engine.world.domain.entity.actors.impl.models

import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity

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