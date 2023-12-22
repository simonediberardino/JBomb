package game.events.game

import game.Bomberman
import game.events.models.GameEvent

class AllEnemiesEliminatedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onAllEnemiesEliminated()
    }
}