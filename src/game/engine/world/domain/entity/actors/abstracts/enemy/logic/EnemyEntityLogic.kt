package game.engine.world.domain.entity.actors.abstracts.enemy.logic

import game.Bomberman
import game.engine.events.game.EnemyDespawnedGameEvent
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.impl.player.BomberEntity

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
}