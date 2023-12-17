package game.entity.models

import game.ui.panels.game.PitchPanel

abstract class Block(coordinates: Coordinates?) : Entity(coordinates) {
    override fun getSize(): Int {
        return SIZE
    }

    override fun eliminated() {
        destroy()
    }

    open fun destroy() {
        despawn()
    }

    companion object {
        val SIZE = PitchPanel.GRID_SIZE
    }
}