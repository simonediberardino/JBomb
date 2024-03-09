package game.domain.world.domain.entity.actors.impl.enemies.boss.base.properties

import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.types.EntityTypes
import game.values.DrawPriority
import java.util.TreeMap

open class BossEntityProperties(
        drawPriority: DrawPriority = Boss.DEFAULT.DRAW_PRIORITY,
        obstacles: Set<Class<out Entity?>?> = Boss.DEFAULT.OBSTACLES,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        imageDirections: List<Direction> = Character.DEFAULT.IMAGE_DIRECTIONS,
        deathSound: SoundModel = Boss.DEFAULT.DEATH_SOUND,
        val healthStatusMap: TreeMap<Int, Int>? = Boss.DEFAULT.HEALTH_STATUS_MAP
): CharacterEntityProperties(
        drawPriority = drawPriority,
        obstacles = obstacles,
        types = types,
        supportedDirections = supportedDirections,
        stepSound = stepSound,
        imageDirections = imageDirections,
        deathSound = deathSound
)