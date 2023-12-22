package game.events.game

import game.Bomberman
import game.events.models.GameEvent


class DefeatGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onDefeatGameEvent()
    }
}