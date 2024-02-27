package game.engine.world.domain.entity.actors.abstracts.enemy.properties

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy

class EnemyEntityState(entity: Entity) : CharacterEntityState(
        entity = entity,
        size = Enemy.DEFAULT.SIZE,
        interactionEntities = Enemy.DEFAULT.INTERACTION_ENTITIES
)