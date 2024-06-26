package game.domain.world.domain.entity.actors.abstracts.character.graphics

import game.audio.AudioManager
import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.geo.Direction
import game.utils.Utility
import game.utils.time.now
import java.awt.image.BufferedImage

open class CharacterGraphicsBehavior(open val entity: Character) : DefaultEntityGraphicsBehavior(), ICharacterGraphicsBehavior {
    override fun getImage(entity: Entity): BufferedImage? {
        return if (entity.image._image != null) {
            entity.image._image
        } else {
            super.loadAndSetImage(entity, this.entity.image.characterOrientedImages()[0])
        }
    }

    override fun resetGraphics(entity: Entity) {
        this.entity.image._image = super.loadAndSetImage(entity, this.entity.image.characterOrientedImages()[0])
    }

    override fun directionUpdateGraphics(previousDirection: Direction?, currDirection: Direction?) {
        setImageDirection(currDirection)
        val baseIcons = entity.image.characterOrientedImages()

        // If the previousDirection and current direction are different, reset the image index and last direction update time.
        if (previousDirection !== currDirection) {
            entity.image.lastImageIndex = (0)
            entity.state.lastDirectionUpdate = now()
        } else if (Utility.timePassed(entity.state.lastImageUpdate) > entity.image.imageRefreshRate) {
            // If it's time to refresh the image, increment the image index.
            entity.image.lastImageIndex++
            entity.logic.onStep()
        } else {
            // Otherwise, don't update the image yet.
            return
        }

        // Ensure the icon index is within bounds.
        if (entity.image.lastImageIndex < 0 || entity.image.lastImageIndex >= baseIcons.size)
            entity.image.lastImageIndex = 0

        loadAndSetImage(entity, baseIcons[entity.image.lastImageIndex])
    }

    override fun setImageDirection(direction: Direction?) {
        val imageDirections = entity.properties.imageDirections

        if (imageDirections.contains(direction))
            entity.state.imageDirection = direction
        else if (entity.state.imageDirection == null)
            entity.state.imageDirection = imageDirections[0]
    }
}