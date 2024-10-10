package game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.GhostEnemy

class GhostEnemyState(
        entity: Entity,
        size: Int = GhostEnemy.DEFAULT.SIZE,
        whitelistObstacles: MutableSet<Class<out Entity>> = GhostEnemy.DEFAULT.WHITELIST_OBSTACLES
) : EnemyEntityState(
        entity = entity,
        size = size,
        whitelistObstacles = whitelistObstacles
)