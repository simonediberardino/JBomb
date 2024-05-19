package game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.FiringEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.state.FiringEnemyState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.SkeletonEnemy
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction

open class SkeletonEnemyState(
        entity: Entity,
        speed: Float = SkeletonEnemy.DEFAULT.SPEED
) : FiringEnemyState(
        entity = entity,
        speed = speed
)