package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.state

import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityProperties
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.types.EntityTypes
import game.values.DrawPriority

class ExplosionProperties(
        drawPriority: DrawPriority = AbstractExplosion.DEFAULT.DRAW_PRIORITY,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = null,
        val explosionClass: Class<out AbstractExplosion>,
        val ignoreCenter: Boolean = AbstractExplosion.DEFAULT.IGNORE_CENTER
): MovingEntityProperties(
        drawPriority = drawPriority,
        types = types,
        supportedDirections = supportedDirections,
        stepSound = stepSound
)