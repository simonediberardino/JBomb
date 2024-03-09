package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder

class UpdateCurrentBombsLengthEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateBombsLengthEvent(Bomberman.getMatch().player ?: return, arg as Int)
        UpdateInfoEventForwarder().invoke(Bomberman.getMatch().player!!.toDto())
    }
}