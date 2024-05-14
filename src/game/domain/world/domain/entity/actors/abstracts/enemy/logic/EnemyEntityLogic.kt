package game.domain.world.domain.entity.actors.abstracts.enemy.logic

import game.JBomb
import game.domain.events.game.DecreaseEnemiesAliveGameEvent
import game.domain.events.game.EnemyDespawnedGameEvent
import game.domain.events.game.IncreaseEnemiesAliveGameEvent
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

abstract class EnemyEntityLogic(override val entity: Enemy) : CharacterEntityLogic(entity) {
    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let {
            attack(it)
        }
    }

    override fun onAdded() {
        super.onAdded()

        IncreaseEnemiesAliveGameEvent().invoke(null)
    }

    override fun onRemoved() {
        super.onRemoved()
        DecreaseEnemiesAliveGameEvent().invoke(null)
    }

    override fun onDespawn() {
        super.onDespawn()
        val match = JBomb.match
        (match.gameTickerObservable ?: return).unregister(entity)
        EnemyDespawnedGameEvent().invoke(null)
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {}
}