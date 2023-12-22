package game.events.game

import game.Bomberman
import game.events.models.GameEvent

class UpdateCurrentBombsLengthEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateBombsLengthEvent(Bomberman.getMatch().player ?: return, arg as Int)
    }
}