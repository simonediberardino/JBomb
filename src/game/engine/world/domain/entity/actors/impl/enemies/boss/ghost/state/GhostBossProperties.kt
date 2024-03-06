package game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.state

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.types.EntityTypes
import game.values.DrawPriority
import java.util.*

class GhostBossProperties(
        drawPriority: DrawPriority = Boss.DEFAULT.DRAW_PRIORITY,
        obstacles: Set<Class<out Entity?>?> = Boss.DEFAULT.OBSTACLES,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        imageDirections: List<Direction> = GhostBoss.DEFAULT.IMAGE_DIRECTIONS,
        deathSound: SoundModel = Boss.DEFAULT.DEATH_SOUND,
        healthStatusMap: TreeMap<Int, Int>? = GhostBoss.DEFAULT.HEALTH_STATUS_MAP
) : BossEntityProperties(
        drawPriority = drawPriority,
        obstacles = obstacles,
        types = types,
        supportedDirections = supportedDirections,
        stepSound = stepSound,
        imageDirections = imageDirections,
        deathSound = deathSound,
        healthStatusMap = healthStatusMap
) {
}