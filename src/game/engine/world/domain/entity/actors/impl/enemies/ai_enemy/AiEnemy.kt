package game.engine.world.domain.entity.actors.impl.enemies.ai_enemy

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.models.*
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.events.game.KilledEnemyEvent
import game.engine.events.game.ScoreGameEvent
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.abstracts.enemy.logic.EnemyEntityLogic
import game.engine.world.domain.entity.actors.impl.enemies.ai_enemy.logic.AiEnemyLogic
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.utils.XMLUtils
import java.util.stream.Collectors

abstract class AiEnemy : Enemy, CPU {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    abstract override val logic: AiEnemyLogic

    companion object {
        const val DIRECTION_REFRESH_RATE = 500
    }
}