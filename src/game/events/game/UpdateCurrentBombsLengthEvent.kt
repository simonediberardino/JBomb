package game.events.game

import game.Bomberman
import game.events.models.GameEvent

class UpdateCurrentBombsLengthEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel.onUpdateBombsLengthEvent(Bomberman.getMatch().player, arg as Int)
    }
}