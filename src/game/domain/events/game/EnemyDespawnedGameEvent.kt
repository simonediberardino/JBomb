package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class EnemyDespawnedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (!Bomberman.match.gameState)
            return
        Bomberman.match.currentLevel!!.eventHandler.onEnemyDespawned()
    }
}