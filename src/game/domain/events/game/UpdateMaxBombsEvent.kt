package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent


class UpdateMaxBombsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        JBomb.match.currentLevel!!.eventHandler.onUpdateMaxBombsGameEvent(arg as Int)
    }
}