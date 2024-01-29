package game.engine.events.game

import game.Bomberman
import game.engine.events.models.GameEvent


class RoundPassedGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onRoundPassedGameEvent()
    }
}