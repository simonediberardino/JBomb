package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent

class ResetBombsVariablesGameEvent: GameEvent {
    override fun invoke(vararg arg: Any?) {
        JBomb.match.currentLevel.eventHandler.resetBombsVariables()
    }
}