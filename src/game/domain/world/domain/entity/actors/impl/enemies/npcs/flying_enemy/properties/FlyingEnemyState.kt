package game.domain.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.FlyingEnemy

class FlyingEnemyState(
        entity: Entity,
        whitelistObstacles: MutableSet<Class<out Entity>> = FlyingEnemy.DEFAULT.WHITELIST_OBSTACLES
) : EnemyEntityState(
        entity = entity,
        whitelistObstacles = whitelistObstacles,
)