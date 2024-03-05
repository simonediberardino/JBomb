package game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.types.EntityTypes
import game.values.DrawPriority

open class OrbEntityProperties(
        types: EntityTypes,
        obstacles: Set<Class<out Entity?>?> = Orb.DEFAULT.OBSTACLES,
        drawPriority: DrawPriority = Orb.DEFAULT.DRAW_PRIORITY,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        imageDirections: List<Direction> = Character.DEFAULT.IMAGE_DIRECTIONS,
        deathSound: SoundModel = Character.DEFAULT.DEATH_SOUND
) : CharacterEntityProperties(
        drawPriority = drawPriority,
        types = types,
        obstacles = obstacles,
        supportedDirections = supportedDirections,
        stepSound = stepSound,
        imageDirections = imageDirections,
        deathSound = deathSound
)