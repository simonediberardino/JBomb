package game.domain.world.domain.entity.actors.abstracts.base.graphics

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.utils.Utility
import game.utils.file_system.Paths
import kotlinx.coroutines.launch
import java.awt.image.BufferedImage

abstract class PeriodicGraphicsBehavior: DefaultEntityGraphicsBehavior() {
    abstract val imagesCount: Int
    abstract val allowUiState: Boolean

    override fun getImage(entity: Entity): BufferedImage? {
        // Check if enough time has passed for an image refresh
        if (Utility.timePassed(entity.state.lastImageUpdate) < entity.image.imageRefreshRate) {
            return entity.image._image!!
        }

        val state = if (allowUiState) "_${entity.state.uiState.toString().lowercase()}" else ""

        val entitiesAssetsPath = entity.image.entitiesAssetsPath
        val images = if (imagesCount > 0) Array(imagesCount) { i ->
            entitiesAssetsPath.replace(
                    "%format%",
                    "_$i${state}"
            )
        } else arrayOf(entitiesAssetsPath)

        // Load the next image in the sequence
        val img = loadAndSetImage(entity = entity, imagePath = images[entity.image.lastImageIndex])
        entity.image.lastImageIndex = (entity.image.lastImageIndex + 1) % images.size

        JBomb.match.scope.launch {
            onImageChanged(entity)
        }

        return img
    }

    open fun onImageChanged(entity: Entity) {}
}