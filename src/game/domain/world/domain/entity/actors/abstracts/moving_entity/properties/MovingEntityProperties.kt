package game.domain.world.domain.entity.actors.abstracts.moving_entity.properties

import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Direction
import game.values.DrawPriority

open class MovingEntityProperties(
        drawPriority: DrawPriority,
        types: EntityTypes,
        val supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        val stepSound: SoundModel? = MovingEntity.DEFAULT.STEP_SOUND
) : EntityProperties(drawPriority, types)