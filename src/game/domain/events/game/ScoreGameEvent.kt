package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent


class ScoreGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        JBomb.match.currentLevel!!.eventHandler.onScoreGameEvent(arg as Int)
    }
}