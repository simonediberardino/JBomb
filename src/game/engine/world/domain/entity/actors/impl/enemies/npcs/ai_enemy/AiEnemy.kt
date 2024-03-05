package game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy

import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.AiEnemyLogic
import game.engine.world.domain.entity.geo.Coordinates

abstract class AiEnemy : Enemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: AiEnemyLogic = AiEnemyLogic(entity = this)

    companion object {
        const val DIRECTION_REFRESH_RATE = 500
    }
}