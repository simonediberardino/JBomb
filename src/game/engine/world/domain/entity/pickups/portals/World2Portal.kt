package game.engine.world.domain.entity.pickups.portals

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import java.awt.Dimension

class World2Portal() : WorldPortal(2) {
    constructor(id: Long) : this() {
        this.info.id = id
    }

    override val defaultCoords: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(6, 7), 0, -PitchPanel.GRID_SIZE / 2)

    override val type: EntityTypes
        get() = EntityTypes.World2Portal
}