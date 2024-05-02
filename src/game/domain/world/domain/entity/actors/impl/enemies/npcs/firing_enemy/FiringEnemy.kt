package game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.AiEnemyLogic
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.logic.FiringEnemyLogic
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.state.FiringEnemyState
import game.domain.world.domain.entity.actors.impl.models.Explosive
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates

abstract class FiringEnemy : AiEnemy, Explosive {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: FiringEnemyLogic = FiringEnemyLogic(entity = this)
    override val state: FiringEnemyState = FiringEnemyState(entity = this)
    override val maxExplosionDistance: Int = 4

    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(
                HardBlock::class.java,
                DestroyableBlock::class.java
        )

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(
                BomberEntity::class.java,
                Bomb::class.java
        )

    internal object DEFAULT {
        const val CAN_SHOOT = true
        const val LAST_FIRE = 0L
    }
}