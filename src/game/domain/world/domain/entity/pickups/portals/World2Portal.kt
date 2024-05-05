package game.domain.world.domain.entity.pickups.portals

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.pickups.portals.imp.world_base.WorldPortal
import game.domain.world.domain.entity.pickups.portals.imp.world_base.state.WorldPortalState
import java.awt.Dimension

class World2Portal() : WorldPortal(2) {
    constructor(id: Long) : this() {
        this.info.id = id
    }

    override val state: WorldPortalState = object : WorldPortalState(entity = this) {
        override val defaultCoords: Coordinates?
            get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(6, 7), 0, -PitchPanel.GRID_SIZE / 2)
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.World2Portal)

    override val imagesCount: Int
        get() = 2
}