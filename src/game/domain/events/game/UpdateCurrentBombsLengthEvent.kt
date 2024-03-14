package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder

class UpdateCurrentBombsLengthEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.match.currentLevel!!.eventHandler.onUpdateBombsLengthEvent(Bomberman.match.player ?: return, arg as Int)
        UpdateInfoEventForwarder().invoke(Bomberman.match.player!!.toEntityNetwork())
    }
}