package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent

class DeathGameEvent : GameEvent {
    override fun invoke(vararg arg: Any?) {
        JBomb.match.currentLevel.eventHandler.onDeathGameEvent()
        JBomb.match.player = null
    }
}