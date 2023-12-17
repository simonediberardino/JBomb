package game.powerups.portal

import game.entity.models.Coordinates
import game.ui.panels.game.PitchPanel
import java.awt.Dimension

class World1Portal : WorldPortal(null, 1) {
    override val defaultCoords: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(3, 4), 0, -PitchPanel.GRID_SIZE / 2)
}