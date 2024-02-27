package game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.state

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.actors.abstracts.moving_entity.properties.MovingEntityProperties
import game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.dto.EntityTypes
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