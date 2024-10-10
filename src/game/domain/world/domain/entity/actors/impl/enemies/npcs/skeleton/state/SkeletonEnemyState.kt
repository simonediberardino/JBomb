package game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.state.FiringEnemyState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.SkeletonEnemy

open class SkeletonEnemyState(
        entity: Entity,
        speed: Float = SkeletonEnemy.DEFAULT.SPEED
) : FiringEnemyState(
        entity = entity,
        speed = speed
)