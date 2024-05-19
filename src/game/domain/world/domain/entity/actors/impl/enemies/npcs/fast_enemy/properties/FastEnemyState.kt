package game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastPurpleBall
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction

class FastEnemyState(
        entity: Entity,
        size: Int = FastPurpleBall.DEFAULT.SIZE,
        speed: Float = FastPurpleBall.DEFAULT.SPEED
) : EnemyEntityState(
        entity = entity,
        size = size,
        speed = speed
)