package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder

class UpdateCurrentBombsLengthEvent : GameEvent {
    override fun invoke(arg: Any?) {
        JBomb.match.currentLevel.eventHandler.onUpdateBombsLengthEvent(JBomb.match.player ?: return, arg as Int)
        UpdateInfoEventForwarder().invoke(JBomb.match.player!!.toEntityNetwork())
    }
}