package game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic

import game.domain.events.game.*
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.utils.dev.Log

open class AiEnemyLogic(override val entity: Enemy) : AiLogic(entity = entity) {
    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let {
            attack(it)
        }
    }

    override fun onSpawn() {
        super.onSpawn()
        IncreaseEnemiesAliveGameEvent().invoke(null)
    }

    override fun onDespawn() {
        super.onDespawn()
        Log.e("onDespawning $entity")
        DecreaseEnemiesAliveGameEvent().invoke(null)
        EnemyDespawnedGameEvent().invoke(null)
    }


    override fun onEliminated() {
        super.onEliminated()
        KilledEnemyEvent().invoke(entity)
        ScoreGameEvent().invoke(entity.state.maxHp)
    }
}