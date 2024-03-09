package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class EnemyDespawnedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (!Bomberman.getMatch().gameState)
            return
        Bomberman.getMatch().currentLevel!!.eventHandler.onEnemyDespawned()
    }
}