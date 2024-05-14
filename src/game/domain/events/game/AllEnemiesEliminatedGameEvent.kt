package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent

class AllEnemiesEliminatedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (JBomb.isGameEnded)
            return

        JBomb.match.currentLevel.eventHandler.onAllEnemiesEliminated()
    }
}