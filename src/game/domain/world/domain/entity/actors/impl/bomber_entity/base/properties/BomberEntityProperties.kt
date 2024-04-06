package game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties

import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.types.EntityTypes
import game.values.DrawPriority

open class BomberEntityProperties(
        drawPriority: DrawPriority = Character.DEFAULT.DRAW_PRIORITY,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = BomberEntity.DEFAULT.STEP_SOUND,
        obstacles: Set<Class<out Entity?>?> = EntityInteractable.DEFAULT.OBSTACLES,
        imageDirections: List<Direction> = Character.DEFAULT.IMAGE_DIRECTIONS,
        deathSound: SoundModel = Character.DEFAULT.DEATH_SOUND
): CharacterEntityProperties(
        drawPriority = drawPriority,
        types = types,
        supportedDirections = supportedDirections,
        stepSound = stepSound,
        obstacles = obstacles,
        imageDirections = imageDirections,
        deathSound = deathSound
)
