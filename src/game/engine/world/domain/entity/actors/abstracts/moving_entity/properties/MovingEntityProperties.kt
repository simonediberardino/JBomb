package game.engine.world.domain.entity.actors.abstracts.moving_entity.properties

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Direction
import game.values.DrawPriority

open class MovingEntityProperties(
        drawPriority: DrawPriority,
        types: EntityTypes,
        val supportedDirections: List<Direction> = Direction.values().asList(),
        val stepSound: SoundModel? = null
) : EntityProperties(drawPriority, types)