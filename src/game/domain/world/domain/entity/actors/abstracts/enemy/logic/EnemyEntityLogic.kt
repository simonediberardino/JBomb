package game.domain.world.domain.entity.actors.abstracts.enemy.logic

import game.Bomberman
import game.domain.events.game.EnemyDespawnedGameEvent
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

abstract class EnemyEntityLogic(override val entity: Enemy) : CharacterEntityLogic(entity) {
    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let { attack(it) }
    }

    override fun onSpawn() {
        super.onSpawn()
        Bomberman.getMatch().increaseEnemiesAlive()
        Bomberman.getMatch().gameTickerObservable?.register(entity)
    }

    override fun onDespawn() {
        super.onDespawn()
        val match = Bomberman.getMatch() ?: return
        match.decreaseEnemiesAlive()
        (match.gameTickerObservable ?: return).unregister(entity)
        EnemyDespawnedGameEvent().invoke(null)
    }

    override fun observerUpdate(arg: Any?) {

    }
}