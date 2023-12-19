package game.events.game

import game.Bomberman
import game.events.models.GameEvent

class EnemyDespawnedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (!Bomberman.getMatch().gameState)
            return
        Bomberman.getMatch().currentLevel.eventHandler.onEnemyDespawned()
    }
}