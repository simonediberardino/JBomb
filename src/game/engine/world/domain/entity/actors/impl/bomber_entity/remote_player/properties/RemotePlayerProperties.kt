package game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player.properties

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.types.EntityTypes
import game.values.DrawPriority

class RemotePlayerProperties(
        drawPriority: DrawPriority = Character.DEFAULT.DRAW_PRIORITY,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        obstacles: Set<Class<out Entity?>?> = EntityInteractable.DEFAULT.OBSTACLES,
        imageDirections: List<Direction> = Character.DEFAULT.IMAGE_DIRECTIONS,
        deathSound: SoundModel = Character.DEFAULT.DEATH_SOUND
) : CharacterEntityProperties(
        types = EntityTypes.BomberEntity,
        drawPriority = drawPriority,
        supportedDirections = supportedDirections,
        stepSound = stepSound,
        obstacles = obstacles,
        imageDirections = imageDirections,
        deathSound = deathSound
) {
    val skinId: Int = 0
}