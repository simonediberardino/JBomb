package game.domain.world.domain.entity.pickups.portals

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.pickups.portals.imp.world_base.WorldPortal
import game.domain.world.domain.entity.pickups.portals.imp.world_base.state.WorldPortalState
import java.awt.Dimension

class World1Portal() : WorldPortal(null, 1) {
    constructor(id: Long) : this() {
        this.info.id = id
    }

    override val state: WorldPortalState = object : WorldPortalState(entity = this) {
        override val defaultCoords: Coordinates?
            get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(3, 4), 0, -PitchPanel.GRID_SIZE / 2)
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.World1Portal)
}