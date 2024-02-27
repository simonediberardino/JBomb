package game.engine.world.domain.entity.actors.abstracts.character.properties

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityProperties
import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Direction
import game.values.DrawPriority

class CharacterEntityProperties(
        drawPriority: DrawPriority,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        val obstacles: Set<Class<out Entity?>?>,
        val imageDirections: List<Direction> = Character.DEFAULT.IMAGE_DIRECTIONS,
        val deathSound: SoundModel = Character.DEFAULT.DEATH_SOUND
) : MovingEntityProperties(
        drawPriority,
        types,
        supportedDirections,
        stepSound
)