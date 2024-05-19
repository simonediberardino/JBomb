package game.domain.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.properties

import game.input.Command
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.FlyingEnemy
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Direction
import java.util.concurrent.atomic.AtomicReference

class FlyingEnemyState(
        entity: Entity,
        whitelistObstacles: MutableSet<Class<out Entity>> = FlyingEnemy.DEFAULT.WHITELIST_OBSTACLES
) : EnemyEntityState(
        entity = entity,
        whitelistObstacles = whitelistObstacles,
)