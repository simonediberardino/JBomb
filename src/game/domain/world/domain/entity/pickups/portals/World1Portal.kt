package game.domain.world.domain.entity.pickups.portals

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.portals.imp.world_base.WorldPortal
import game.domain.world.domain.entity.pickups.portals.imp.world_base.state.WorldPortalState
import game.domain.world.types.EntityTypes
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.utils.file_system.Paths
import java.awt.Dimension
import java.awt.image.BufferedImage

class World1Portal() : WorldPortal(null, 1) {
    constructor(id: Long) : this() {
        this.info.id = id
    }

    override val state: WorldPortalState = object : WorldPortalState(entity = this) {
        override val defaultCoords: Coordinates?
            get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(3, 4), 0, -PitchPanel.GRID_SIZE / 2)
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.World1Portal)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            val imagesCount = 3
            val images = Array(imagesCount) { i ->
                Paths.getWorldSelectorPortalPath(worldId, i)
            }

            // Check if enough time has passed for an image refresh
            if (Utility.timePassed(state.lastImageUpdate) < image.imageRefreshRate) {
                return image._image!!
            }

            // Load the next image in the sequence
            val img = loadAndSetImage(entity = entity, imagePath = images[image.lastImageIndex])
            image.lastImageIndex = (image.lastImageIndex + 1) % images.size

            return img
        }
    }

}