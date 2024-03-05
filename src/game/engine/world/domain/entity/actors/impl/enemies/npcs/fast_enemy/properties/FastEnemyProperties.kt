package game.engine.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.properties

import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastEnemy
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.types.EntityTypes
import game.values.DrawPriority

class FastEnemyProperties(
        drawPriority: DrawPriority = Character.DEFAULT.DRAW_PRIORITY,
        types: EntityTypes,
        supportedDirections: List<Direction> = MovingEntity.DEFAULT.SUPPORTED_DIRECTIONS,
        stepSound: SoundModel? = Character.DEFAULT.STEP_SOUND,
        obstacles: Set<Class<out Entity?>?> = EntityInteractable.DEFAULT.OBSTACLES,
        imageDirections: List<Direction> = FastEnemy.DEFAULT.IMAGE_DIRECTIONS,
        deathSound: SoundModel = Character.DEFAULT.DEATH_SOUND
) : CharacterEntityProperties(
        drawPriority = drawPriority,
        types = types,
        supportedDirections = supportedDirections,
        stepSound = stepSound,
        obstacles = obstacles,
        imageDirections = imageDirections,
        deathSound = deathSound
) {
}