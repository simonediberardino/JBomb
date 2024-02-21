package game.engine.world.domain.entity.pickups.portals

import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import java.awt.Dimension

class World1Portal() : WorldPortal(null, 1) {
    constructor(id: Long) : this() {
        this.info.id = id
    }

    override val defaultCoords: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(3, 4), 0, -PitchPanel.GRID_SIZE / 2)

    override val type: EntityTypes
        get() = EntityTypes.World1Portal
}