package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class AllEnemiesEliminatedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (Bomberman.isGameEnded)
            return

        Bomberman.match.currentLevel.eventHandler.onAllEnemiesEliminated()
    }
}