package game.domain.world.domain.entity.actors.abstracts.character.properties

import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityProperties
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Direction
import game.values.DrawPriority

open class CharacterEntityProperties(
        drawPriority: DrawPriority = Character.DEFAULT.DRAW_PRIORITY,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        val imageDirections: List<Direction> = Character.DEFAULT.IMAGE_DIRECTIONS,
        val deathSound: SoundModel = Character.DEFAULT.DEATH_SOUND
) : MovingEntityProperties(
        drawPriority,
        types,
        supportedDirections,
        stepSound
) {
    open var name: String? = null
}