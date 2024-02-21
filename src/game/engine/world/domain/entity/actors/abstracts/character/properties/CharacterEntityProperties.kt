package game.engine.world.domain.entity.actors.abstracts.character.properties

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityProperties
import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Direction
import game.values.DrawPriority

class CharacterEntityProperties(
        drawPriority: DrawPriority,
        types: EntityTypes,
        supportedDirections: List<Direction> = Direction.values().asList(),
        stepSound: SoundModel? = SoundModel.STEP_SOUND,
        val obstacles: Set<Class<out Entity?>?>,
        val imageDirections: List<Direction> = Direction.values().asList(),
        val deathSound: SoundModel = SoundModel.ENTITY_DEATH
) : MovingEntityProperties(
        drawPriority,
        types,
        supportedDirections,
        stepSound
)