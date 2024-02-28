package game.engine.world.domain.entity.actors.abstracts.enemy.properties

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy

open class EnemyEntityState(
        entity: Entity,
        size: Int = Enemy.DEFAULT.SIZE,
        interactionEntities: MutableSet<Class<out Entity>> = Enemy.DEFAULT.INTERACTION_ENTITIES,
        speed: Float = Character.DEFAULT.SPEED,
        maxHp: Int = Character.DEFAULT.MAX_HP
) : CharacterEntityState(
        entity = entity,
        size = size,
        interactionEntities = interactionEntities,
        speed = speed,
        maxHp = maxHp
)