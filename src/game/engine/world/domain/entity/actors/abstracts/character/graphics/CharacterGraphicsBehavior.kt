package game.engine.world.domain.entity.actors.abstracts.character.graphics

import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.geo.Direction
import game.utils.Utility
import java.awt.image.BufferedImage

class CharacterGraphicsBehavior(val character: Character) : DefaultEntityGraphicsBehavior(), ICharacterGraphicsBehavior {
    override fun getImage(entity: Entity): BufferedImage? {
        return if (entity.image._image != null) {
            entity.image._image
        } else {
            super.loadAndSetImage(entity, character.image.characterOrientedImages[0])
        }
    }

    override fun directionUpdateGraphics(previousDirection: Direction?, currDirection: Direction?) {
        setImageDirection(currDirection)
        val baseIcons = character.image.characterOrientedImages

        // If the previousDirection and current direction are different, reset the image index and last direction update time.
        if (previousDirection !== currDirection) {
            character.image.lastImageIndex = (0)
            character.state.lastDirectionUpdate = System.currentTimeMillis()
        } else if (Utility.timePassed(character.state.lastImageUpdate) > character.image.imageRefreshRate) {
            // If it's time to refresh the image, increment the image index.
            character.image.lastImageIndex++
            playStepSound()
        } else {
            // Otherwise, don't update the image yet.
            return
        }

        // Ensure the icon index is within bounds.
        if (character.image.lastImageIndex < 0 || character.image.lastImageIndex >= baseIcons.size)
            character.image.lastImageIndex = 0

        loadAndSetImage(character, baseIcons[character.image.lastImageIndex])
    }

    override fun setImageDirection(direction: Direction?) {
        val imageDirections = character.properties.imageDirections

        if (imageDirections.contains(direction))
            character.state.imageDirection = direction
        else if (character.state.imageDirection == null)
            character.state.imageDirection = imageDirections[0]
    }

    override fun playStepSound() {
        AudioManager.getInstance().play(SoundModel.STEP_SOUND)
    }
}