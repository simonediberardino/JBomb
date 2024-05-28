package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent

class InitBombsVariablesGameEvent: GameEvent {
    override fun invoke(vararg arg: Any?) {
        JBomb.match.currentLevel.eventHandler.initBombsVariables()
    }
}