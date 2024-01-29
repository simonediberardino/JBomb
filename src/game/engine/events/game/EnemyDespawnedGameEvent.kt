package game.engine.events.game

import game.Bomberman
import game.engine.events.models.GameEvent

class EnemyDespawnedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (!Bomberman.getMatch().gameState)
            return
        Bomberman.getMatch().currentLevel!!.eventHandler.onEnemyDespawned()
    }
}