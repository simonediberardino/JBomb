package game.powerups.portal

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.ui.panels.game.PitchPanel
import java.awt.Dimension

class World2Portal() : WorldPortal(2) {
    constructor(id: Long) : this() {
        this.id = id
    }

    override val defaultCoords: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(6, 7), 0, -PitchPanel.GRID_SIZE / 2)

    override fun getType(): EntityTypes = EntityTypes.World2Portal
}