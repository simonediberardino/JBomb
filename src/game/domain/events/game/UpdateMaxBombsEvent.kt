package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent


class UpdateMaxBombsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateMaxBombsGameEvent(arg as Int)
    }
}